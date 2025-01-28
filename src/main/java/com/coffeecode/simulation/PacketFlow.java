package com.coffeecode.simulation;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

import com.coffeecode.algorithms.RoutingStrategy;
import com.coffeecode.core.Edge;
import com.coffeecode.core.Node;

public class PacketFlow {
    private final RoutingStrategy routingStrategy;
    private final Queue<Packet> packetQueue;
    private final AtomicLong processedPackets;
    private final AtomicLong failedPackets;
    private static final long TIMEOUT_MS = 5000;

    public PacketFlow(RoutingStrategy routingStrategy) {
        this.routingStrategy = routingStrategy;
        this.packetQueue = new ConcurrentLinkedQueue<>();
        this.processedPackets = new AtomicLong(0);
        this.failedPackets = new AtomicLong(0);
    }

    public void sendPacket(Packet packet) {
        List<Node> route = routingStrategy.findPath(packet.getSource(), packet.getDestination());

        if (route.isEmpty() || !canTransmitPacket(packet, route)) {
            handleFailedPacket(packet);
            return;
        }

        packetQueue.offer(packet);
        packet.setStatus(Packet.PacketStatus.IN_TRANSIT);
    }

    private boolean canTransmitPacket(Packet packet, List<Node> route) {
        for (int i = 0; i < route.size() - 1; i++) {
            Node current = route.get(i);
            Node next = route.get(i + 1);
            Edge edge = findEdge(current, next);

            if (edge == null || !edge.isConnected() || packet.getSize() > edge.getBandwidth()) {
                return false;
            }
        }
        return true;
    }

    private Edge findEdge(Node source, Node destination) {
        return source.getEdges().stream()
                .filter(e -> e.getDestination().equals(destination))
                .findFirst()
                .orElse(null);
    }

    public void processPackets() {
        while (!packetQueue.isEmpty()) {
            Packet packet = packetQueue.poll();
            if (packet == null)
                continue;

            if (isPacketTimedOut(packet)) {
                handleFailedPacket(packet);
                continue;
            }

            deliverPacket(packet);
        }
    }

    private boolean isPacketTimedOut(Packet packet) {
        return System.currentTimeMillis() - packet.getCreationTime() > TIMEOUT_MS;
    }

    private void deliverPacket(Packet packet) {
        if (packet.getDestination().isActive()) {
            packet.setStatus(Packet.PacketStatus.DELIVERED);
            processedPackets.incrementAndGet();
        } else {
            handleFailedPacket(packet);
        }
    }

    private void handleFailedPacket(Packet packet) {
        packet.setStatus(Packet.PacketStatus.FAILED);
        failedPackets.incrementAndGet();
    }

    public long getProcessedPacketsCount() {
        return processedPackets.get();
    }

    public long getFailedPacketsCount() {
        return failedPackets.get();
    }
}

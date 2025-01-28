package com.coffeecode.simulation;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.coffeecode.algorithms.RoutingStrategy;
import com.coffeecode.core.Node;
import com.coffeecode.metrics.NetworkMonitor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacketFlow {
    private final RoutingStrategy routingStrategy;
    private final NetworkMonitor monitor;
    private final ConcurrentLinkedQueue<Packet> packetQueue = new ConcurrentLinkedQueue<>();
    private final AtomicLong processedPackets = new AtomicLong(0);
    private final AtomicLong failedPackets = new AtomicLong(0);

    private static final long TIMEOUT_MS = 5000;

    public void sendPacket(Packet packet) {
        List<Node> route = routingStrategy.findPath(
                packet.getSource(),
                packet.getDestination());

        if (route.isEmpty() || !canTransmitPacket(packet, route)) {
            handleFailedPacket(packet);
            return;
        }

        packet.setStatus(Packet.PacketStatus.IN_TRANSIT);
        packetQueue.offer(packet);
        monitor.recordPacketTransmission(packet.getSize());
    }

    private boolean canTransmitPacket(Packet packet, List<Node> route) {
        if (!routingStrategy.isValidPath(route)) {
            return false;
        }
        double requiredBandwidth = calculateRequiredBandwidth(packet);
        return hasAvailableBandwidth(packet, route, requiredBandwidth);
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

    private void deliverPacket(Packet packet) {
        if (packet.getDestination().isActive()) {
            packet.setStatus(Packet.PacketStatus.DELIVERED);
            processedPackets.incrementAndGet();
            double latency = calculateLatency(packet);
            double bandwidth = calculateBandwidth(packet);
            monitor.recordSuccessfulDelivery(latency, bandwidth);
        } else {
            handleFailedPacket(packet);
        }
    }

    private boolean isPacketTimedOut(Packet packet) {
        return System.currentTimeMillis() - packet.getCreationTime() > TIMEOUT_MS;
    }

    private double calculateLatency(Packet packet) {
        return System.currentTimeMillis() - packet.getCreationTime();
    }

    private double calculateBandwidth(Packet packet) {
        return packet.getSize() / calculateLatency(packet);
    }

    private boolean hasAvailableBandwidth(Packet packet, List<Node> route, double requiredBandwidth) {
        return route.stream().allMatch(node -> node.getEdges().stream()
                .anyMatch(edge -> edge.getBandwidth() >= requiredBandwidth));
    }

    private void handleFailedPacket(Packet packet) {
        packet.setStatus(Packet.PacketStatus.FAILED);
        failedPackets.incrementAndGet();
        if (monitor != null) {
            monitor.recordFailedDelivery();
        }
    }

    private double calculateRequiredBandwidth(Packet packet) {
        return packet.getSize() / 1000.0; // Convert to KB/s
    }

    public long getProcessedPacketsCount() {
        return processedPackets.get();
    }

    public long getFailedPacketsCount() {
        return failedPackets.get();
    }
}

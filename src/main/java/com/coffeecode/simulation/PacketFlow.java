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
    private final ConcurrentLinkedQueue<Packet> packetQueue;
    private final AtomicLong processedPackets;
    private final AtomicLong failedPackets;

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
        return routingStrategy.isValidPath(route) &&
                hasAvailableBandwidth(packet, route);
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
            monitor.recordSuccessfulDelivery(packet);
        } else {
            handleFailedPacket(packet);
        }
    }

    private void handleFailedPacket(Packet packet) {
        packet.setStatus(Packet.PacketStatus.FAILED);
        failedPackets.incrementAndGet();
        monitor.recordFailedDelivery(packet);
    }

    private boolean isPacketTimedOut(Packet packet) {
        return System.currentTimeMillis() - packet.getCreationTime() > TIMEOUT_MS;
    }
}

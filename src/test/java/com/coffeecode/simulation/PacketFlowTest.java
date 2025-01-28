package com.coffeecode.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.algorithms.RoutingStrategy;
import com.coffeecode.core.Edge;
import com.coffeecode.core.Node;
import com.coffeecode.core.RouterNode;
import com.coffeecode.core.ServerNode;

class PacketFlowTest {
    private PacketFlow packetFlow;
    private RoutingStrategy routingStrategy;
    private Node source;
    private Node destination;
    private Edge edge;

    @BeforeEach
    void setUp() {
        routingStrategy = mock(RoutingStrategy.class);
        packetFlow = new PacketFlow(routingStrategy);

        source = new RouterNode("R1", 10);
        destination = new ServerNode("S1", 100, 1000);

        edge = Edge.builder()
                .source(source)
                .destination(destination)
                .bandwidth(100)
                .latency(5)
                .active(true)
                .build();

        source.addEdge(edge);
    }

    @Test
    void testSuccessfulPacketDelivery() {
        when(routingStrategy.findPath(source, destination))
                .thenReturn(Arrays.asList(source, destination));

        Packet packet = Packet.builder()
                .id("P1")
                .source(source)
                .destination(destination)
                .size(50)
                .status(Packet.PacketStatus.CREATED)
                .creationTime(System.currentTimeMillis())
                .build();

        packetFlow.sendPacket(packet);
        packetFlow.processPackets();

        assertEquals(Packet.PacketStatus.DELIVERED, packet.getStatus());
        assertEquals(1, packetFlow.getProcessedPacketsCount());
    }

    @Test
    void testFailedPacketDelivery() {
        when(routingStrategy.findPath(source, destination))
                .thenReturn(Collections.emptyList());

        Packet packet = Packet.builder()
                .id("P1")
                .source(source)
                .destination(destination)
                .size(50)
                .status(Packet.PacketStatus.CREATED)
                .creationTime(System.currentTimeMillis())
                .build();

        packetFlow.sendPacket(packet);
        assertEquals(Packet.PacketStatus.FAILED, packet.getStatus());
        assertEquals(1, packetFlow.getFailedPacketsCount());
    }
}

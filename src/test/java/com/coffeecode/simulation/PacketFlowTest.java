package com.coffeecode.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.node.RouterNode;
import com.coffeecode.domain.node.ServerNode;
import com.coffeecode.domain.packet.Packet;
import com.coffeecode.infrastructure.monitoring.NetworkMetrics;
import com.coffeecode.service.routing.RoutingStrategy;
import com.coffeecode.service.simulation.PacketFlow;

@ExtendWith(MockitoExtension.class)
class PacketFlowTest {
        private RoutingStrategy routingStrategy;
        private NetworkMetrics networkMetrics; // Use interface instead of concrete class
        private PacketFlow packetFlow;
        private RouterNode source;
        private ServerNode destination;

        @BeforeEach
        void setUp() {
                routingStrategy = mock(RoutingStrategy.class);
                networkMetrics = mock(NetworkMetrics.class);
                packetFlow = new PacketFlow(routingStrategy, networkMetrics);
                source = new RouterNode("R1", 10);
                destination = new ServerNode("S1", 100, 1000);
        }

        @Test
        void testFailedPacketDelivery() {
                // Arrange
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

                // Act
                packetFlow.sendPacket(packet);

                // Assert
                assertEquals(Packet.PacketStatus.FAILED, packet.getStatus());
                assertEquals(1, packetFlow.getFailedPacketsCount());
                verify(networkMonitor).recordFailedDelivery();
        }
}

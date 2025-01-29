package com.coffeecode.domain.node.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.connection.ConnectionManager;
import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.node.base.NodeType;
import com.coffeecode.domain.node.properties.ClientNodeProperties;

@ExtendWith(MockitoExtension.class)
class ClientNodeTest {

    @Mock
    private ConnectionManager connectionManager;

    @Mock
    private Edge edge;

    private ClientNode clientNode;
    private ClientNodeProperties properties;

    @BeforeEach
    void setUp() {
        properties = ClientNodeProperties.builder()
                .dataRate(100.0)
                .maxBandwidth(1000.0)
                .build();
        clientNode = new ClientNode(properties, connectionManager);
    }

    @Test
    void shouldCreateClientWithValidProperties() {
        assertNotNull(clientNode.getIdentity());
        assertEquals(NodeType.CLIENT, clientNode.getType());
        assertTrue(clientNode.isActive());
    }

    @Test
    void shouldTransmitDataWithinCapacity() {
        assertTrue(clientNode.canTransmit(50.0));
        clientNode.recordTransmission(50.0);
        assertTrue(clientNode.canTransmit(40.0));
    }

    @Test
    void shouldRejectTransmissionBeyondCapacity() {
        clientNode.recordTransmission(80.0);
        assertFalse(clientNode.canTransmit(30.0));
        assertThrows(IllegalArgumentException.class,
                () -> clientNode.recordTransmission(30.0));
    }

    @Test
    void shouldManageDataTransmission() {
        // Test within capacity
        assertTrue(clientNode.canTransmit(50.0));
        clientNode.recordTransmission(50.0);

        // Test near limit
        assertTrue(clientNode.canTransmit(40.0));
        assertFalse(clientNode.canTransmit(60.0));

        // Test reset
        clientNode.resetUsage();
        assertTrue(clientNode.canTransmit(100.0));
    }

    @Test
    void shouldRejectInvalidTransmission() {
        assertThrows(IllegalArgumentException.class,
                () -> clientNode.recordTransmission(150.0));
    }

    @Test
    void shouldManageConnectionsProperly() {
        clientNode.addConnection(edge);
        verify(connectionManager).addConnection(edge);

        clientNode.removeConnection(edge);
        verify(connectionManager).removeConnection(edge);
    }

    @Test
    void shouldResetUsageWhenActivated() {
        clientNode.recordTransmission(50.0);
        clientNode.setActive(true);
        assertTrue(clientNode.canTransmit(100.0));
    }
}

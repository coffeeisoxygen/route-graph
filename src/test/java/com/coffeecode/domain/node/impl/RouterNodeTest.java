package com.coffeecode.domain.node.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.connection.ConnectionManager;
import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.node.base.NodeType;
import com.coffeecode.domain.node.properties.RouterNodeProperties;

@ExtendWith(MockitoExtension.class)
class RouterNodeTest {

    @Mock
    private ConnectionManager connectionManager;

    @Mock
    private Edge edge;

    private RouterNode routerNode;
    private RouterNodeProperties properties;

    @BeforeEach
    void setUp() {
        properties = RouterNodeProperties.builder()
                .routingCapacity(5)
                .bufferSize(1000.0)
                .supportsQos(true)
                .build();
        routerNode = new RouterNode(properties, connectionManager);
    }

    @Test
    void shouldCreateRouterWithValidProperties() {
        assertNotNull(routerNode.getIdentity());
        assertEquals(NodeType.ROUTER, routerNode.getType());
        assertTrue(routerNode.isActive());
    }

    @Test
    void shouldManageRoutingCapacity() {
        // Test initial state
        assertTrue(routerNode.canAddRoute());

        // Add routes up to capacity
        for (int i = 0; i < properties.getRoutingCapacity(); i++) {
            assertTrue(routerNode.addRoute());
        }

        // Verify at capacity
        assertFalse(routerNode.canAddRoute());
        assertFalse(routerNode.addRoute());
    }

    @Test
    void shouldHandleConnections() {
        routerNode.addConnection(edge);
        verify(connectionManager).validateMaxConnections(properties.getRoutingCapacity());
        verify(connectionManager).addConnection(edge);

        routerNode.removeConnection(edge);
        verify(connectionManager).removeConnection(edge);
    }

    @Test
    void shouldCalculateBufferUtilization() {
        when(connectionManager.getConnectionCount()).thenReturn(2);
        assertEquals(40.0, routerNode.getBufferUtilization());
    }
}

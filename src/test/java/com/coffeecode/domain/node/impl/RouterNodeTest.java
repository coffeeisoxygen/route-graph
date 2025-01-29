package com.coffeecode.domain.node.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.common.NetNodeType;
import com.coffeecode.domain.node.base.BaseNodeTest;
import com.coffeecode.domain.node.properties.RouterNodeProperties;

class RouterNodeTest extends BaseNodeTest {
    private RouterNode routerNode;
    private RouterNodeProperties properties;

    @BeforeEach
    void init() {
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
        assertEquals(NetNodeType.ROUTER, routerNode.getType());
        assertTrue(routerNode.isActive());
    }

    @Test
    void shouldManageRoutes() {
        assertTrue(routerNode.canAddRoute());
        assertTrue(routerNode.addRoute());

        // Fill to capacity
        for (int i = 1; i < properties.getRoutingCapacity(); i++) {
            routerNode.addRoute();
        }

        assertFalse(routerNode.canAddRoute());
    }

    @Test
    void shouldManageConnections() {
        routerNode.addConnection(mockEdge);
        verify(connectionManager).addConnection(mockEdge);

        routerNode.removeConnection(mockEdge);
        verify(connectionManager).removeConnection(mockEdge);
    }
}

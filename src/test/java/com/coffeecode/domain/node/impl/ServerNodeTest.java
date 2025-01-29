package com.coffeecode.domain.node.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.common.NetNodeType;
import com.coffeecode.domain.node.base.BaseNodeTest;
import com.coffeecode.domain.node.properties.ServerNodeProperties;

class ServerNodeTest extends BaseNodeTest {
    private ServerNode serverNode;
    private ServerNodeProperties properties;

    @BeforeEach
    void init() {
        properties = ServerNodeProperties.builder()
                .capacity(100)
                .processingPower(1000.0)
                .maxConnections(10)
                .build();
        serverNode = new ServerNode(properties, connectionManager);
    }

    @Test
    void shouldCreateServerWithValidProperties() {
        assertNotNull(serverNode.getIdentity());
        assertEquals(NetNodeType.SERVER, serverNode.getType());
        assertTrue(serverNode.isActive());
    }

    @Test
    void shouldManageRequests() {
        assertTrue(serverNode.canHandleRequest());
        assertTrue(serverNode.addRequest());

        // Fill to capacity
        for (int i = 1; i < properties.getCapacity(); i++) {
            serverNode.addRequest();
        }

        assertFalse(serverNode.canHandleRequest());
        assertEquals(100.0, serverNode.getLoadPercentage());
    }

    @Test
    void shouldCompleteRequests() {
        serverNode.addRequest();
        serverNode.completeRequest();
        assertTrue(serverNode.canHandleRequest());
        assertEquals(0.0, serverNode.getLoadPercentage());
    }
}

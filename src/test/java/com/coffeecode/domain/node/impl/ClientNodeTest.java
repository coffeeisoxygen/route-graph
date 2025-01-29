package com.coffeecode.domain.node.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.node.base.BaseNodeTest;
import com.coffeecode.domain.node.base.NetNodeType;
import com.coffeecode.domain.node.properties.ClientNodeProperties;

class ClientNodeTest extends BaseNodeTest {
    private ClientNode clientNode;
    private ClientNodeProperties properties;

    @BeforeEach
    void init() {
        properties = ClientNodeProperties.builder()
                .dataRate(100.0)
                .maxBandwidth(1000.0)
                .build();
        clientNode = new ClientNode(properties, connectionManager);
    }

    @Test
    void shouldCreateClientWithValidProperties() {
        assertNotNull(clientNode.getIdentity());
        assertEquals(NetNodeType.CLIENT, clientNode.getType());
        assertTrue(clientNode.isActive());
    }

    @Test
    void shouldManageTransmissions() {
        assertTrue(clientNode.canTransmit(50.0));
        clientNode.recordTransmission(50.0);

        assertTrue(clientNode.canTransmit(40.0));
        assertFalse(clientNode.canTransmit(60.0));
    }

    @Test
    void shouldResetUsage() {
        clientNode.recordTransmission(50.0);
        clientNode.resetUsage();
        assertTrue(clientNode.canTransmit(100.0));
    }

    @Test
    void shouldRejectExcessiveTransmission() {
        assertThrows(IllegalArgumentException.class,
                () -> clientNode.recordTransmission(150.0));
    }
}

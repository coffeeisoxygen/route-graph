package com.coffeecode.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.coffeecode.domain.node.ClientNode;

class ClientNodeTest {
    @Test
    void testClientCreation() {
        ClientNode client = new ClientNode("C1", 100);
        assertEquals("C1", client.getId());
        assertEquals(100, client.getDataRate());
        assertEquals(0, client.getCurrentUsage());
    }

    @Test
    void testCanTransmit() {
        ClientNode client = new ClientNode("C1", 100);
        assertTrue(client.canTransmit(50));
        assertTrue(client.canTransmit(100));
        assertFalse(client.canTransmit(150));
    }
}

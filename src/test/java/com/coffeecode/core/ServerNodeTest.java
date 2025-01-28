package com.coffeecode.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.coffeecode.domain.node.ServerNode;

class ServerNodeTest {
    @Test
    void testServerCreation() {
        ServerNode server = new ServerNode("S1", 100, 1000);
        assertEquals("S1", server.getId());
        assertEquals(100, server.getCapacity());
        assertEquals(1000, server.getProcessingPower());
        assertEquals(0, server.getCurrentLoad());
    }

    @Test
    void testCanHandleRequest() {
        ServerNode server = new ServerNode("S1", 100, 1000);
        assertTrue(server.canHandleRequest());
    }
}

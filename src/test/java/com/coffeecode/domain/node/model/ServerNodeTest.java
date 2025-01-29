package com.coffeecode.domain.node.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.node.base.NodeType;
import com.coffeecode.domain.node.impl.ServerNode;

class ServerNodeTest {
    private ServerNode serverNode;
    private final String testId = UUID.randomUUID().toString();
    private final int testCapacity = 10;
    private final double testProcessingPower = 100.0;

    @BeforeEach
    void setUp() {
        serverNode = new ServerNode(testId, testCapacity, testProcessingPower);
    }

    @Test
    void constructor_ValidParameters_CreatesNode() {
        assertNotNull(serverNode);
        assertEquals(testId, serverNode.getIdString());
        assertEquals(NodeType.SERVER, serverNode.getType());
        assertEquals(testCapacity, serverNode.getCapacity());
        assertEquals(testProcessingPower, serverNode.getProcessingPower());
        assertTrue(serverNode.isActive());
    }

    @Test
    void constructor_InvalidParameters_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new ServerNode(testId, -1, testProcessingPower));
        assertThrows(IllegalArgumentException.class, () -> new ServerNode(testId, testCapacity, -1.0));
    }

    @Test
    void canHandleRequest_BelowCapacity_ReturnsTrue() {
        assertTrue(serverNode.canHandleRequest());
    }

    @Test
    void addRequest_BelowCapacity_ReturnsTrue() {
        assertTrue(serverNode.addRequest());
        assertEquals(1, serverNode.getCurrentLoad().get());
    }

    @Test
    void addRequest_AtCapacity_ReturnsFalse() {
        for (int i = 0; i < testCapacity; i++) {
            serverNode.addRequest();
        }
        assertFalse(serverNode.addRequest());
    }

    @Test
    void completeRequest_DecreasesLoad() {
        serverNode.addRequest();
        serverNode.completeRequest();
        assertEquals(0, serverNode.getCurrentLoad().get());
    }

    @Test
    void getLoadPercentage_CalculatesCorrectly() {
        serverNode.addRequest(); // Add one request
        assertEquals(10.0, serverNode.getLoadPercentage()); // 1/10 * 100 = 10%
    }
}

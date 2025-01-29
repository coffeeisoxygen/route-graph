package com.coffeecode.domain.node.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClientNodeTest {
    private ClientNode clientNode;
    private final String testId = UUID.randomUUID().toString();
    private final double testDataRate = 100.0;

    @BeforeEach
    void setUp() {
        clientNode = new ClientNode(testId, testDataRate);
    }

    @Test
    void constructor_ValidParameters_CreatesNode() {
        assertNotNull(clientNode);
        assertEquals(testId, clientNode.getIdString());
        assertEquals(NodeType.CLIENT, clientNode.getType());
        assertEquals(testDataRate, clientNode.getDataRate());
        assertTrue(clientNode.isActive());
    }

    @Test
    void constructor_NegativeDataRate_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
            new ClientNode(testId, -1.0));
    }

    @Test
    void canTransmit_WithinLimit_ReturnsTrue() {
        assertTrue(clientNode.canTransmit(testDataRate - 1));
    }

    @Test
    void canTransmit_ExceedsLimit_ReturnsFalse() {
        assertFalse(clientNode.canTransmit(testDataRate + 1));
    }

    @Test
    void recordTransmission_ValidAmount_UpdatesUsage() {
        double transmissionSize = 50.0;
        clientNode.recordTransmission(transmissionSize);
        assertEquals(transmissionSize, clientNode.getCurrentUsage());
    }

    @Test
    void recordTransmission_ExceedsLimit_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
            clientNode.recordTransmission(testDataRate + 1));
    }

    @Test
    void resetUsage_ResetsToZero() {
        clientNode.recordTransmission(50.0);
        clientNode.resetUsage();
        assertEquals(0.0, clientNode.getCurrentUsage());
    }
}

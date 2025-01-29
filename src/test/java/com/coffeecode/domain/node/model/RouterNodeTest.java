package com.coffeecode.domain.node.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RouterNodeTest {
    private RouterNode routerNode;
    private final String testId = UUID.randomUUID().toString();

    private final int testRoutingCapacity = 5;

    @BeforeEach
    void setUp() {
        routerNode = new RouterNode(testId, testRoutingCapacity);
    }

    @Test
    void constructor_ValidParameters_CreatesNode() {
        assertNotNull(routerNode);
        assertEquals(testId, routerNode.getIdString());
        assertEquals(NodeType.ROUTER, routerNode.getType());
        assertEquals(testRoutingCapacity, routerNode.getRoutingCapacity());
        assertTrue(routerNode.isActive());
    }

    @Test
    void constructor_NegativeCapacity_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
            new RouterNode(testId, -1));
    }

    @Test
    void canAddRoute_BelowCapacity_ReturnsTrue() {
        assertTrue(routerNode.canAddRoute());
    }

    @Test
    void addRoute_BelowCapacity_ReturnsTrue() {
        assertTrue(routerNode.addRoute());
        assertEquals(1, routerNode.getCurrentRoutes().get());
    }

    @Test
    void addRoute_AtCapacity_ReturnsFalse() {
        for (int i = 0; i < testRoutingCapacity; i++) {
            routerNode.addRoute();
        }
        assertFalse(routerNode.addRoute());
    }

    @Test
    void removeRoute_DecreasesRouteCount() {
        routerNode.addRoute();
        routerNode.removeRoute();
        assertEquals(0, routerNode.getCurrentRoutes().get());
    }
}

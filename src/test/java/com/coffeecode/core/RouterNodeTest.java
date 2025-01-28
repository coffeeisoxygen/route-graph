package com.coffeecode.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.coffeecode.domain.node.RouterNode;

class RouterNodeTest {
    @Test
    void testRouterCreation() {
        RouterNode router = new RouterNode("R1", 5);
        assertEquals("R1", router.getId());
        assertEquals(5, router.getRoutingCapacity());
        assertEquals(0, router.getCurrentRoutes());
    }

    @Test
    void testCanAddRoute() {
        RouterNode router = new RouterNode("R1", 2);
        assertTrue(router.canAddRoute());
    }
}

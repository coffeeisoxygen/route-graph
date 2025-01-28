package com.coffeecode.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EdgeTest {
    private Node source;
    private Node destination;

    @BeforeEach
    void setUp() {
        source = new RouterNode("R1", 10);
        destination = new ServerNode("S1", 100, 1000);
    }

    @Test
    void testValidEdge() {
        Edge edge = Edge.builder()
                .source(source)
                .destination(destination)
                .bandwidth(100)
                .latency(10)
                .active(true)
                .build();

        assertTrue(edge.isValid());
        assertTrue(edge.isConnected());
    }

    @Test
    void testEdgeWeight() {
        Edge edge = Edge.builder()
                .source(source)
                .destination(destination)
                .bandwidth(100)
                .latency(10)
                .active(true)
                .build();

        assertEquals(0.1, edge.getWeight());
    }
}

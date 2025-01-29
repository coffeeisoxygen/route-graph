package com.coffeecode.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.node.model.AbstractNode;
import com.coffeecode.domain.node.model.NodeType;

class TestNode extends AbstractNode {
    public TestNode(String id) {
        super(id, NodeType.ROUTER);
    }
}

class AbstractNodeTest {
    private TestNode source;
    private TestNode destination;

    @BeforeEach
    void setUp() {
        source = new TestNode("source");
        destination = new TestNode("dest");
    }

    @Test
    void testNodeCreation() {
        assertEquals("source", source.getId());
        assertTrue(source.isActive());
        assertTrue(source.getEdges().isEmpty());
    }

    @Test
    void testAddValidEdge() {
        Edge edge = Edge.builder()
                .source(source)
                .destination(destination)
                .bandwidth(100)
                .latency(10)
                .active(true)
                .build();

        source.addEdge(edge);
        assertEquals(1, source.getEdges().size());
    }

    @Test
    void testAddInvalidEdgeWithZeroBandwidth() {
        Edge invalidEdge = Edge.builder()
                .source(source)
                .destination(destination)
                .bandwidth(0)
                .latency(10)
                .active(true)
                .build();

        assertThrows(IllegalArgumentException.class,
                () -> source.addEdge(invalidEdge));
    }

    @Test
    void testAddInvalidEdgeWithNegativeLatency() {
        Edge invalidEdge = Edge.builder()
                .source(source)
                .destination(destination)
                .bandwidth(100)
                .latency(-1)
                .active(true)
                .build();

        assertThrows(IllegalArgumentException.class,
                () -> source.addEdge(invalidEdge));
    }
}

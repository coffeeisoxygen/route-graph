package com.coffeecode.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TestNode extends AbstractNode {
    public TestNode(String id) {
        super(id, NodeType.ROUTER);
    }
}

class AbstractNodeTest {
    @Test
    void testNodeCreation() {
        TestNode node = new TestNode("test1");
        assertEquals("test1", node.getId());
        assertTrue(node.isActive());
        assertTrue(node.getEdges().isEmpty());
    }

    @Test
    void testAddValidEdge() {
        TestNode source = new TestNode("source");
        TestNode dest = new TestNode("dest");

        Edge edge = Edge.builder()
                .source(source)
                .destination(dest)
                .bandwidth(100)
                .latency(10)
                .active(true)
                .build();

        source.addEdge(edge);
        assertEquals(1, source.getEdges().size());
    }

    @Test
    void testAddInvalidEdge() {
        TestNode source = new TestNode("source");
        Edge invalidEdge = Edge.builder()
                .source(source)
                .bandwidth(-1)
                .build();

        assertThrows(IllegalArgumentException.class, () -> source.addEdge(invalidEdge));
    }
}

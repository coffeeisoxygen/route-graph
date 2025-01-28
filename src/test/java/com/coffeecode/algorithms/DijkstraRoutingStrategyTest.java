package com.coffeecode.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.node.Node;
import com.coffeecode.domain.node.RouterNode;
import com.coffeecode.domain.node.ServerNode;
import com.coffeecode.service.routing.DijkstraRoutingStrategy;

class DijkstraRoutingStrategyTest {
    private DijkstraRoutingStrategy strategy;
    private RouterNode source;
    private RouterNode intermediate;
    private ServerNode destination;

    @BeforeEach
    void setUp() {
        strategy = new DijkstraRoutingStrategy();
        source = new RouterNode("R1", 10);
        intermediate = new RouterNode("R2", 10);
        destination = new ServerNode("S1", 100, 1000);

        // Create edges
        Edge edge1 = Edge.builder()
                .source(source)
                .destination(intermediate)
                .bandwidth(100)
                .latency(5)
                .active(true)
                .build();

        Edge edge2 = Edge.builder()
                .source(intermediate)
                .destination(destination)
                .bandwidth(100)
                .latency(5)
                .active(true)
                .build();

        source.addEdge(edge1);
        intermediate.addEdge(edge2);
    }

    @Test
    void testFindValidPath() {
        List<Node> path = strategy.findPath(source, destination);
        assertFalse(path.isEmpty());
        assertEquals(3, path.size());
        assertEquals(source, path.get(0));
        assertEquals(destination, path.get(2));
    }

    @Test
    void testCalculatePathCost() {
        List<Node> path = strategy.findPath(source, destination);
        double cost = strategy.calculatePathCost(path);
        assertTrue(cost > 0);
    }
}

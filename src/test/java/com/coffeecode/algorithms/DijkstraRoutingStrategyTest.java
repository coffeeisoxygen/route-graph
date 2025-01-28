package com.coffeecode.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.core.NetworkTopology;
import com.coffeecode.core.Node;
import com.coffeecode.core.RouterNode;
import com.coffeecode.core.ServerNode;

class DijkstraRoutingStrategyTest {
    private NetworkTopology network;
    private RoutingStrategy router;
    private Node source, intermediate, destination;

    @BeforeEach
    void setUp() {
        network = new NetworkTopology();
        router = new DijkstraRoutingStrategy();

        source = new RouterNode("R1", 10);
        intermediate = new RouterNode("R2", 10);
        destination = new ServerNode("S1", 100, 1000);

        network.addNode(source);
        network.addNode(intermediate);
        network.addNode(destination);

        network.connect("R1", "R2", 100, 5);
        network.connect("R2", "S1", 100, 5);
    }

    @Test
    void testFindValidPath() {
        List<Node> path = router.findPath(source, destination);
        assertEquals(3, path.size());
        assertEquals(source, path.get(0));
        assertEquals(destination, path.get(2));
    }

    @Test
    void testNoPathWhenDisconnected() {
        intermediate.setActive(false);
        List<Node> path = router.findPath(source, destination);
        assertTrue(path.isEmpty());
    }
}

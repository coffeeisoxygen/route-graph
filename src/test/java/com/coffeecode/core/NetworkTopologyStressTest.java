package com.coffeecode.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.node.model.ClientNode;
import com.coffeecode.domain.node.model.Node;
import com.coffeecode.domain.node.model.RouterNode;
import com.coffeecode.domain.node.model.ServerNode;
import com.coffeecode.domain.topology.NetworkTopology;

class NetworkTopologyStressTest {
    private NetworkTopology network;
    private static final int LARGE_NETWORK_SIZE = 100;
    private Map<String, Node> testNodes;

    @BeforeEach
    void setUp() {
        network = new NetworkTopology();
        testNodes = new HashMap<>();
        createLargeNetwork();
    }

    private void createLargeNetwork() {
        // Create mixed node types
        for (int i = 0; i < LARGE_NETWORK_SIZE; i++) {
            Node node;
            if (i % 3 == 0) {
                node = new RouterNode("R" + i, 50);
            } else if (i % 3 == 1) {
                node = new ServerNode("S" + i, 1000, 2000);
            } else {
                node = new ClientNode("C" + i, 100);
            }
            testNodes.put(node.getId(), node);
            network.addNode(node);
        }
    }

    @Test
    void testLargeNetworkCreation() {
        assertEquals(LARGE_NETWORK_SIZE, network.getNodes().size());
    }

    @Test
    void testMassiveConnectionCreation() {
        List<String> nodeIds = new ArrayList<>(testNodes.keySet());
        int connectionCount = 0;

        // Create mesh-like connections
        for (int i = 0; i < nodeIds.size(); i++) {
            for (int j = i + 1; j < Math.min(i + 5, nodeIds.size()); j++) {
                network.connect(nodeIds.get(i), nodeIds.get(j), 100, 5);
                connectionCount++;
            }
        }

        assertTrue(connectionCount > 0);
        assertEquals(connectionCount, network.getEdges().size());
    }

    @Test
    void testNodeFailureScenario() {
        // Create initial connections
        List<String> nodeIds = new ArrayList<>(testNodes.keySet());
        for (int i = 0; i < 10; i++) {
            network.connect(nodeIds.get(i), nodeIds.get(i + 1), 100, 5);
        }

        // Simulate node failures
        for (int i = 0; i < 5; i++) {
            Node node = testNodes.get(nodeIds.get(i));
            node.setActive(false);
        }

        // Verify network state
        int activeConnections = (int) network.getEdges().stream()
                .filter(Edge::isConnected)
                .count();

        assertTrue(activeConnections < network.getEdges().size());
    }

    @Test
    void testNetworkPartitioning() {
        // Create two clusters of nodes
        List<String> nodeIds = new ArrayList<>(testNodes.keySet());
        int midPoint = nodeIds.size() / 2;

        // Connect first cluster
        for (int i = 0; i < midPoint - 1; i++) {
            network.connect(nodeIds.get(i), nodeIds.get(i + 1), 100, 5);
        }

        // Connect second cluster
        for (int i = midPoint; i < nodeIds.size() - 1; i++) {
            network.connect(nodeIds.get(i), nodeIds.get(i + 1), 100, 5);
        }

        // Add single bridge connection
        network.connect(nodeIds.get(midPoint - 1), nodeIds.get(midPoint), 100, 5);

        // Test bridge failure
        Node bridgeNode = testNodes.get(nodeIds.get(midPoint));
        bridgeNode.setActive(false);

        assertFalse(network.isConnected(nodeIds.get(0), nodeIds.get(nodeIds.size() - 1)));
    }
}

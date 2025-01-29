package com.coffeecode.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.node.model.ClientNode;
import com.coffeecode.domain.node.model.RouterNode;
import com.coffeecode.domain.node.model.ServerNode;
import com.coffeecode.domain.topology.NetworkTopology;

class NetworkTopologyTest {
    private NetworkTopology network;
    private RouterNode router1;
    private ServerNode server1;
    private ClientNode client1;

    @BeforeEach
    void setUp() {
        network = new NetworkTopology();
        router1 = new RouterNode("R1", 10);
        server1 = new ServerNode("S1", 100, 1000);
        client1 = new ClientNode("C1", 100);
    }

    @Test
    void testAddValidNodes() {
        network.addNode(router1);
        network.addNode(server1);
        network.addNode(client1);

        assertEquals(3, network.getNodes().size());
    }

    @Test
    void testValidConnection() {
        network.addNode(router1);
        network.addNode(server1);
        network.connect("R1", "S1", 100, 5);

        assertTrue(network.isConnected("R1", "S1"));
    }
}

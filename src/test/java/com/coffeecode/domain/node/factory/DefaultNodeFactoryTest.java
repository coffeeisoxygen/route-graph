package com.coffeecode.domain.node.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.factory.node.DefaultNodeFactory;
import com.coffeecode.domain.factory.node.NodeFactory;
import com.coffeecode.domain.node.base.Node;
import com.coffeecode.domain.node.base.NodeType;
import com.coffeecode.domain.node.impl.ClientNode;
import com.coffeecode.domain.node.impl.RouterNode;
import com.coffeecode.domain.node.impl.ServerNode;

class DefaultNodeFactoryTest {

    private NodeFactory nodeFactory;
    private Map<String, Object> properties;

    @BeforeEach
    void setUp() {
        nodeFactory = new DefaultNodeFactory();
        properties = new HashMap<>();
        properties.put("dataRate", 100.0);
        properties.put("capacity", 1000);
        properties.put("processingPower", 2.5);
        properties.put("routingCapacity", 500);
    }

    @Test
    void shouldCreateClientNode() {
        Node node = nodeFactory.createNode(NodeType.CLIENT, "client1", properties);

        assertNotNull(node);
        assertTrue(node instanceof ClientNode);
        assertEquals("client1", node.getId());
        assertEquals(100.0, ((ClientNode) node).getDataRate());
    }

    @Test
    void shouldCreateServerNode() {
        Node node = nodeFactory.createNode(NodeType.SERVER, "server1", properties);

        assertNotNull(node);
        assertTrue(node instanceof ServerNode);
        assertEquals("server1", node.getId());
        assertEquals(1000, ((ServerNode) node).getCapacity());
        assertEquals(2.5, ((ServerNode) node).getProcessingPower());
    }

    @Test
    void shouldCreateRouterNode() {
        Node node = nodeFactory.createNode(NodeType.ROUTER, "router1", properties);

        assertNotNull(node);
        assertTrue(node instanceof RouterNode);
        assertEquals("router1", node.getId());
        assertEquals(500, ((RouterNode) node).getRoutingCapacity());
    }

    @Test
    void shouldCreateMultipleNodes() {
        List<Node> nodes = nodeFactory.createNodes(NodeType.CLIENT, 3, properties);

        assertEquals(3, nodes.size());
        assertTrue(nodes.stream().allMatch(n -> n instanceof ClientNode));
        assertTrue(nodes.stream().map(Node::getId).distinct().count() == 3);
    }
}

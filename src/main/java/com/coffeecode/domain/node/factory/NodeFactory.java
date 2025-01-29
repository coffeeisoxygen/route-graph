package com.coffeecode.domain.node.factory;

import java.util.List;
import java.util.Map;

import com.coffeecode.domain.node.base.Node;
import com.coffeecode.domain.node.base.NodeType;
import com.coffeecode.domain.node.properties.BaseNodeProperties;

public interface NodeFactory {
    /**
     * Creates a node with specific properties
     *
     * @param properties Node properties including type-specific attributes
     * @return Created node instance
     */
    Node createNode(BaseNodeProperties properties);

    /**
     * Creates multiple nodes of the same type
     *
     * @param type      Node type to create
     * @param count     Number of nodes to create
     * @param baseProps Base properties for all nodes
     * @return List of created nodes
     */
    List<Node> createNodes(NodeType type, int count, Map<String, Object> baseProps);

    /**
     * Generates default name for a node
     *
     * @param type     Node type
     * @param sequence Sequence number
     * @return Generated name (e.g. "client-1", "router-2")
     */
    String generateDefaultName(NodeType type, int sequence);
}

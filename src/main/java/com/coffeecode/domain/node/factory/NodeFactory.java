package com.coffeecode.domain.node.factory;

import java.util.List;
import java.util.Map;

import com.coffeecode.domain.node.model.Node;
import com.coffeecode.domain.node.model.NodeType;

public interface NodeFactory {
    Node createNode(NodeType type, String id, Map<String, Object> properties);

    List<Node> createNodes(NodeType type, int count, Map<String, Object> properties);
}

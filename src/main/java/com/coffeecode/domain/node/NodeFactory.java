package com.coffeecode.domain.node;

import java.util.List;
import java.util.Map;

public interface NodeFactory {
    Node createNode(NodeType type, String id, Map<String, Object> properties);

    List<Node> createNodes(NodeType type, int count, Map<String, Object> properties);
}

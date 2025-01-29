package com.coffeecode.domain.factory.node;

import com.coffeecode.domain.node.base.Node;
import com.coffeecode.domain.node.base.NodeType;
import com.coffeecode.domain.node.properties.ClientNodeProperties;
import com.coffeecode.domain.node.properties.RouterNodeProperties;
import com.coffeecode.domain.node.properties.ServerNodeProperties;

import java.util.List;

public interface NodeFactory {
    Node createRouter(RouterNodeProperties props);

    Node createClient(ClientNodeProperties props);

    Node createServer(ServerNodeProperties props);

    List<Node> createBatch(NodeType type, int count, Object properties);
}

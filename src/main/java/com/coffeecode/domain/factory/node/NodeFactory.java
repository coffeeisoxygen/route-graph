package com.coffeecode.domain.factory.node;

import com.coffeecode.domain.entities.node.base.Node;
import com.coffeecode.domain.entities.node.base.NodeType;
import com.coffeecode.domain.entities.node.impl.ClientNodeProperties;
import com.coffeecode.domain.entities.node.impl.RouterNodeProperties;
import com.coffeecode.domain.entities.node.impl.ServerNodeProperties;
import com.coffeecode.domain.node.properties.*;
import java.util.List;

public interface NodeFactory {
    Node createRouter(RouterNodeProperties props);

    Node createClient(ClientNodeProperties props);

    Node createServer(ServerNodeProperties props);

    List<Node> createBatch(NodeType type, int count, Object properties);
}

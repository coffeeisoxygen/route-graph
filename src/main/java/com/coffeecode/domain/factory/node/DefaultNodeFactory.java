package com.coffeecode.domain.factory.node;

import com.coffeecode.domain.entities.node.base.Node;
import com.coffeecode.domain.entities.node.impl.ClientNode;
import com.coffeecode.domain.entities.node.impl.ClientNodeProperties;
import com.coffeecode.domain.entities.node.impl.RouterNode;
import com.coffeecode.domain.entities.node.impl.RouterNodeProperties;
import com.coffeecode.domain.entities.node.impl.ServerNode;
import com.coffeecode.domain.entities.node.impl.ServerNodeProperties;
import com.coffeecode.domain.node.impl.*;
import com.coffeecode.domain.node.properties.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DefaultNodeFactory implements NodeFactory {

    @Override
    public Node createRouter(RouterNodeProperties props) {
        if (!props.isValid()) {
            throw new IllegalArgumentException("Invalid router properties");
        }
        return new RouterNode(props);
    }

    @Override
    public Node createClient(ClientNodeProperties props) {
        if (!props.isValid()) {
            throw new IllegalArgumentException("Invalid client properties");
        }
        return new ClientNode(props);
    }

    @Override
    public Node createServer(ServerNodeProperties props) {
        if (!props.isValid()) {
            throw new IllegalArgumentException("Invalid server properties");
        }
        return new ServerNode(props);
    }

    @Override
    public List<Node> createBatch(NodeType type, int count, Object properties) {
        return IntStream.range(0, count)
                .mapToObj(i -> createNode(type, properties))
                .toList();
    }

    private Node createNode(NodeType type, Object properties) {
        return switch (type) {
            case ROUTER -> createRouter((RouterNodeProperties) properties);
            case CLIENT -> createClient((ClientNodeProperties) properties);
            case SERVER -> createServer((ServerNodeProperties) properties);
        };
    }
}

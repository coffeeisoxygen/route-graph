package com.coffeecode.domain.factory.node;

import com.coffeecode.domain.node.base.Node;
import com.coffeecode.domain.node.base.NodeType;
import com.coffeecode.domain.node.impl.ClientNode;
import com.coffeecode.domain.node.impl.RouterNode;
import com.coffeecode.domain.node.impl.ServerNode;
import com.coffeecode.domain.node.properties.ClientNodeProperties;
import com.coffeecode.domain.node.properties.RouterNodeProperties;
import com.coffeecode.domain.node.properties.ServerNodeProperties;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DefaultNodeFactory implements NodeFactory {

    @Override
    public Node createRouter(RouterNodeProperties props) {
        validateProperties(props);
        return new RouterNode(props);
    }

    @Override
    public Node createClient(ClientNodeProperties props) {
        validateProperties(props);
        return new ClientNode(props);
    }

    @Override
    public Node createServer(ServerNodeProperties props) {
        validateProperties(props);
        return new ServerNode(props);
    }

    @Override
    public List<Node> createBatch(NodeType type, int count, Object properties) {
        validateBatchParameters(type, count, properties);
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

    private void validateProperties(Object props) {
        if (props == null) {
            throw new IllegalArgumentException("Properties cannot be null");
        }
    }

    private void validateBatchParameters(NodeType type, int count, Object properties) {
        if (type == null) {
            throw new IllegalArgumentException("Node type cannot be null");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be positive");
        }
        validateProperties(properties);
    }
}

package com.coffeecode.domain.factory.node;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.coffeecode.domain.connection.ConnectionManager;
import com.coffeecode.domain.node.base.NetNode;
import com.coffeecode.domain.node.base.NetNodeType;
import com.coffeecode.domain.node.impl.ClientNode;
import com.coffeecode.domain.node.impl.RouterNode;
import com.coffeecode.domain.node.impl.ServerNode;
import com.coffeecode.domain.node.properties.ClientNodeProperties;
import com.coffeecode.domain.node.properties.RouterNodeProperties;
import com.coffeecode.domain.node.properties.ServerNodeProperties;

@Component
public class DefaultNodeFactory implements NetNodeFactory {
    private final ConnectionManager connectionManager;

    public DefaultNodeFactory(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public NetNode createRouter(RouterNodeProperties props) {
        validateProperties(props);
        return new RouterNode(props, connectionManager);
    }

    @Override
    public NetNode createClient(ClientNodeProperties props) {
        validateProperties(props);
        return new ClientNode(props, connectionManager);
    }

    @Override
    public NetNode createServer(ServerNodeProperties props) {
        validateProperties(props);
        return new ServerNode(props, connectionManager);
    }

    @Override
    public List<NetNode> createBatch(NetNodeType type, int count, Object properties) {
        validateBatchParameters(type, count, properties);
        return IntStream.range(0, count)
                .mapToObj(i -> createNode(type, properties))
                .toList();
    }

    private NetNode createNode(NetNodeType type, Object properties) {
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

    private void validateBatchParameters(NetNodeType type, int count, Object properties) {
        if (type == null) {
            throw new IllegalArgumentException("Node type cannot be null");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be positive");
        }
        validateProperties(properties);
    }

}

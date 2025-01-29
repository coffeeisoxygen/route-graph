package com.coffeecode.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.coffeecode.domain.connection.ConnectionManager;
import com.coffeecode.domain.factory.edge.DefaultEdgeFactory;
import com.coffeecode.domain.factory.edge.EdgeFactory;
import com.coffeecode.domain.factory.node.DefaultNodeFactory;
import com.coffeecode.domain.factory.node.NodeFactory;
import com.coffeecode.domain.node.base.NodeType;
import com.coffeecode.domain.node.properties.ClientNodeProperties;
import com.coffeecode.domain.node.properties.RouterNodeProperties;
import com.coffeecode.domain.node.properties.ServerNodeProperties;

@Configuration
public class NetworkConfig {

    @Bean
    public NodeFactory nodeFactory(ConnectionManager connectionManager) {
        return new DefaultNodeFactory(connectionManager);
    }

    @Bean
    public EdgeFactory edgeFactory() {
        return new DefaultEdgeFactory();
    }

    @Bean
    @Scope("prototype")
    public RouterNodeProperties defaultRouterProperties() {
        return RouterNodeProperties.builder()
                .routingCapacity(NodeType.ROUTER.getDefaultCapacity())
                .bufferSize(1024.0)
                .supportsQos(true)
                .build();
    }

    @Bean
    @Scope("prototype")
    public ClientNodeProperties defaultClientProperties() {
        return ClientNodeProperties.builder()
                .dataRate(100.0)
                .maxBandwidth((double) NodeType.CLIENT.getDefaultCapacity())
                .build();
    }

    @Bean
    @Scope("prototype")
    public ServerNodeProperties defaultServerProperties() {
        return ServerNodeProperties.builder()
                .capacity(NodeType.SERVER.getDefaultCapacity())
                .processingPower(1000.0)
                .maxConnections(100)
                .build();
    }

    @Bean
    @Scope("prototype")
    public ConnectionManager connectionManager() {
        return new ConnectionManager(null);
    }
}

@Component
@Scope("prototype")
public class DefaultConnectionManager implements ConnectionManager {
    private final List<Edge> connections;
    private final Node owner;

    public DefaultConnectionManager(Node owner) {
        this.owner = owner;
        this.connections = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public synchronized boolean hasConnection(Edge edge) {
        return connections.contains(edge);
    }

    @Override
    public synchronized Optional<Edge> findConnection(Node target) {
        return connections.stream()
            .filter(edge -> edge.getDestination().equals(target))
            .findFirst();
    }

    @Override
    public synchronized boolean isConnectedTo(Node target) {
        return connections.stream()
            .anyMatch(edge -> edge.getDestination().equals(target));
    }

    @Override
    public synchronized void validateMaxConnections(int maxConnections) {
        if (maxConnections > 0 && connections.size() >= maxConnections) {
            throw new IllegalStateException(
                String.format("Maximum connections limit (%d) reached", maxConnections)
            );
        }
    }

    // Existing methods remain unchanged...
    @Override
    public List<Edge> getConnections() {
        return Collections.unmodifiableList(connections);
    }

    @Override
    public synchronized void addConnection(Edge edge) {
        validateConnection(edge);
        connections.add(edge);
    }

    @Override
    public synchronized void removeConnection(Edge edge) {
        connections.remove(edge);
    }

    @Override
    public synchronized int getConnectionCount() {
        return connections.size();
    }

    private void validateConnection(Edge edge) {
        if (!edge.isValid()) {
            throw new IllegalArgumentException("Invalid edge configuration");
        }
        if (edge.getSource() != owner && edge.getDestination() != owner) {
            throw new IllegalArgumentException("Edge must connect to this node");
        }
        if (connections.contains(edge)) {
            throw new IllegalArgumentException("Connection already exists");
        }
    }
}

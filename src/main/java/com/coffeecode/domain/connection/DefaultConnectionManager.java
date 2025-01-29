package com.coffeecode.domain.connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.node.base.Node;

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

    public void validateConnection(Edge edge) {
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
                    String.format("Maximum connections limit (%d) reached", maxConnections));
        }
    }
}

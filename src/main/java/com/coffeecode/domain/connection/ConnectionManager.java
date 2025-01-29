package com.coffeecode.domain.connection;

import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.node.base.Node;
import lombok.Getter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

/**
 * Manages node connections in the network.
 * Thread-safe implementation for connection operations.
 */
@Component
@Getter
public class ConnectionManager {
    private final List<Edge> connections;
    private final Node owner;

    public ConnectionManager(Node owner) {
        this.owner = owner;
        this.connections = Collections.synchronizedList(new ArrayList<>());
    }

    public List<Edge> getConnections() {
        return Collections.unmodifiableList(connections);
    }

    public synchronized void addConnection(Edge edge) {
        validateConnection(edge);
        connections.add(edge);
    }

    public synchronized void removeConnection(Edge edge) {
        connections.remove(edge);
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

    public synchronized boolean hasConnection(Edge edge) {
        return connections.contains(edge);
    }

    public synchronized int getConnectionCount() {
        return connections.size();
    }

    public synchronized Optional<Edge> findConnection(Node target) {
        return connections.stream()
                .filter(edge -> edge.getDestination().equals(target))
                .findFirst();
    }

    public synchronized boolean isConnectedTo(Node target) {
        return connections.stream()
                .anyMatch(edge -> edge.getDestination().equals(target));
    }

    public synchronized void validateMaxConnections(int maxConnections) {
        if (maxConnections > 0 && connections.size() >= maxConnections) {
            throw new IllegalStateException("Maximum connections limit reached");
        }
    }
}

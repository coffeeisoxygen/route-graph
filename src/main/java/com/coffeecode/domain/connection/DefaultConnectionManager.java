package com.coffeecode.domain.connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.coffeecode.domain.edge.NetEdge;
import com.coffeecode.domain.node.base.NetNode;

@Component
@Scope("prototype")
public class DefaultConnectionManager implements ConnectionManager {
    private final List<NetEdge> connections;
    private final NetNode owner;

    public DefaultConnectionManager(NetNode owner) {
        this.owner = owner;
        this.connections = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public List<NetEdge> getConnections() {
        return Collections.unmodifiableList(connections);
    }

    @Override
    public synchronized void addConnection(NetEdge edge) {
        validateConnection(edge);
        connections.add(edge);
    }

    @Override
    public synchronized void removeConnection(NetEdge edge) {
        connections.remove(edge);
    }

    @Override
    public synchronized int getConnectionCount() {
        return connections.size();
    }

    private void validateConnection(NetEdge edge) {
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
    public synchronized boolean hasConnection(NetEdge edge) {
        return connections.contains(edge);
    }

    @Override
    public synchronized Optional<NetEdge> findConnection(NetNode target) {
        return connections.stream()
                .filter(edge -> edge.getDestination().equals(target))
                .findFirst();
    }

    @Override
    public synchronized boolean isConnectedTo(NetNode target) {
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

package com.coffeecode.domain.node.router.component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.coffeecode.domain.edge.NetworkEdge;
import com.coffeecode.domain.model.NetworkIdentity;

import lombok.Getter;

@Getter
public class ConnectionManager implements RouterComponent {
    private volatile boolean active;
    private final Set<NetworkEdge> connections;

    public ConnectionManager() {
        this.connections = ConcurrentHashMap.newKeySet();
    }

    public boolean addConnection(NetworkEdge edge) {
        if (!active || edge == null) {
            return false;
        }
        return connections.add(edge);
    }

    public boolean removeConnection(NetworkEdge edge) {
        return connections.remove(edge);
    }

    public int getConnectionCount() {
        return connections.size();
    }

    public boolean isNodeReachable(NetworkIdentity nodeId) {
        return connections.stream()
                .filter(NetworkEdge::isActive)
                .anyMatch(edge -> edge.getDestination()
                        .getIdentity()
                        .equals(nodeId));
    }

    @Override
    public void clear() {
        this.active = false; // Set inactive first
        this.connections.clear();
    }

    @Override
    public void initialize() {
        this.connections.clear(); // Clear first
        this.active = true;
    }

    @Override
    public boolean isActive() {
        return active;
    }
}

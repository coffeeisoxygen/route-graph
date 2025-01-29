package com.coffeecode.domain.node.client.component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.coffeecode.domain.edge.NetworkEdge;

import lombok.Getter;

@Getter
public class ConnectionManager implements ClientComponent {
    private volatile boolean active;
    private final Set<NetworkEdge> connections;

    public ConnectionManager() {
        this.connections = ConcurrentHashMap.newKeySet();
    }

    public boolean addConnection(NetworkEdge edge) {
        if (!isActive() || edge == null) {
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

    @Override
    public void initialize() {
        connections.clear();
        active = true;
    }

    @Override
    public void clear() {
        active = false;
        connections.clear();
    }

    @Override
    public boolean isActive() {
        return active;
    }
}

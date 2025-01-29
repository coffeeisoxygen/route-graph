package com.coffeecode.domain.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.coffeecode.domain.common.NetID;
import com.coffeecode.domain.common.NetNodeType;
import com.coffeecode.domain.connection.ConnectionCapability;
import com.coffeecode.domain.connection.ConnectionManager;
import com.coffeecode.domain.validation.ValidationException;

import lombok.Getter;

@Getter
public abstract class NetworkNode {
    protected final NetID identity;
    protected final ConnectionManager connections;
    protected final ConnectionCapability capability;
    protected final AtomicBoolean active;

    protected NetworkNode(NetNodeType type, ConnectionCapability capability,
            ConnectionManager connectionManager) {
        if (type == null || capability == null || connectionManager == null) {
            throw new ValidationException("Node initialization parameters cannot be null");
        }
        this.identity = NetID.create(type);
        this.capability = capability;
        this.connections = connectionManager;
        this.active = new AtomicBoolean(true);
    }

    public NetNodeType getType() {
        return identity.getType();
    }

    public boolean isActive() {
        return active.get();
    }

    public void setActive(boolean state) {
        boolean oldState = active.getAndSet(state);
        if (oldState != state) {
            onStateChange(state);
        }
    }

    public List<NetworkEdge> getConnections() {
        validateState();
        return connections.getConnections();
    }

    public void addConnection(NetworkEdge edge) {
        validateState();
        if (!canAcceptConnection()) {
            throw new ValidationException("Node cannot accept more connections");
        }
        connections.addConnection(edge);
    }

    public void removeConnection(NetworkEdge edge) {
        validateState();
        connections.removeConnection(edge);
    }

    protected void validateState() {
        if (!isActive()) {
            throw new IllegalStateException("Node is not active");
        }
    }

    public boolean canInitiateConnection() {
        return capability.isCanInitiateConnection();
    }

    protected boolean canAcceptConnection() {
        return connections.getConnectionCount() < capability.getMaxConnections();
    }

    protected abstract void onStateChange(boolean newState);
}

package com.coffeecode.domain.node.impl;

import com.coffeecode.domain.common.Identity;
import com.coffeecode.domain.connection.ConnectionManager;
import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.node.base.Node;
import com.coffeecode.domain.node.base.NodeType;
import com.coffeecode.domain.node.properties.ClientNodeProperties;

import lombok.Getter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Scope("prototype")
@Getter
public class ClientNode implements Node {
    private final Identity identity;
    private final ConnectionManager connectionManager;
    private final ClientNodeProperties properties;
    private final AtomicReference<Double> currentUsage;
    private boolean active;

    public ClientNode(ClientNodeProperties props, ConnectionManager connectionManager) {
        this.identity = Identity.create(NodeType.CLIENT.getNamePrefix());
        this.connectionManager = connectionManager;
        this.properties = props;
        this.currentUsage = new AtomicReference<>(0.0);
        this.active = true;
    }

    @Override
    public NodeType getType() {
        return NodeType.CLIENT;
    }

    @Override
    public List<Edge> getConnections() {
        return connectionManager.getConnections();
    }

    @Override
    public void addConnection(Edge edge) {
        connectionManager.addConnection(edge);
    }

    @Override
    public void removeConnection(Edge edge) {
        connectionManager.removeConnection(edge);
    }

    @Override
    public void setActive(boolean active) {
        if (active) {
            resetUsage();
        }
        this.active = active;
    }

    public synchronized boolean canTransmit(double dataSize) {
        return (currentUsage.get() + dataSize) <= properties.getDataRate();
    }

    public synchronized void recordTransmission(double dataSize) {
        if (!canTransmit(dataSize)) {
            throw new IllegalArgumentException("Transmission would exceed capacity");
        }
        currentUsage.updateAndGet(v -> v + dataSize);
    }

    public synchronized void resetUsage() {
        currentUsage.set(0.0);
    }
}

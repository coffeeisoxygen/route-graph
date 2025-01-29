package com.coffeecode.domain.node.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.coffeecode.domain.common.NetID;
import com.coffeecode.domain.common.NetNodeType;
import com.coffeecode.domain.connection.ConnectionManager;
import com.coffeecode.domain.edge.NetEdge;
import com.coffeecode.domain.node.base.NetNode;
import com.coffeecode.domain.node.properties.ClientNodeProperties;

import lombok.Getter;

@Component
@Scope("prototype")
@Getter
public class ClientNode implements NetNode {
    private final NetID identity;
    private final ConnectionManager connectionManager;
    private final ClientNodeProperties properties;
    private final AtomicReference<Double> currentUsage;
    private boolean active;

    public ClientNode(ClientNodeProperties props, ConnectionManager connectionManager) {
        this.identity = NetID.create(NetNodeType.CLIENT.getNamePrefix());
        this.connectionManager = connectionManager;
        this.properties = props;
        this.currentUsage = new AtomicReference<>(0.0);
        this.active = true;
    }

    @Override
    public NetNodeType getType() {
        return NetNodeType.CLIENT;
    }

    @Override
    public List<NetEdge> getConnections() {
        return connectionManager.getConnections();
    }

    @Override
    public void addConnection(NetEdge edge) {
        connectionManager.addConnection(edge);
    }

    @Override
    public void removeConnection(NetEdge edge) {
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

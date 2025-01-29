package com.coffeecode.domain.node.impl;

import com.coffeecode.domain.common.Identity;
import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.node.base.Node;
import com.coffeecode.domain.node.base.NodeType;
import com.coffeecode.domain.node.properties.ServerNodeProperties;

import lombok.Getter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.coffeecode.domain.common.connection.ConnectionManager;

@Component
@Scope("prototype")
@Getter
public class ServerNode implements Node {
    private final Identity identity;
    private final ConnectionManager connectionManager;
    private final ServerNodeProperties properties;
    private final AtomicInteger currentLoad;
    private boolean active;

    public ServerNode(ServerNodeProperties props, ConnectionManager connectionManager) {
        this.identity = Identity.create(NodeType.SERVER.getNamePrefix());
        this.connectionManager = connectionManager;
        this.properties = props;
        this.currentLoad = new AtomicInteger(0);
        this.active = true;
    }

    @Override
    public NodeType getType() {
        return NodeType.SERVER;
    }

    @Override
    public List<Edge> getConnections() {
        return connectionManager.getConnections();
    }

    @Override
    public void addConnection(Edge edge) {
        connectionManager.validateMaxConnections(properties.getMaxConnections());
        connectionManager.addConnection(edge);
    }

    @Override
    public void removeConnection(Edge edge) {
        connectionManager.removeConnection(edge);
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean canHandleRequest() {
        return currentLoad.get() < properties.getCapacity();
    }

    public boolean addRequest() {
        return currentLoad.get() < properties.getCapacity() &&
                currentLoad.incrementAndGet() <= properties.getCapacity();
    }

    public void completeRequest() {
        currentLoad.updateAndGet(load -> Math.max(0, load - 1));
    }

    public double getLoadPercentage() {
        return (currentLoad.get() * 100.0) / properties.getCapacity();
    }
}

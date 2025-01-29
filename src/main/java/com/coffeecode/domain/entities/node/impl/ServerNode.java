package com.coffeecode.domain.entities.node.impl;

import com.coffeecode.domain.common.Identity;
import com.coffeecode.domain.entities.edge.Edge;
import com.coffeecode.domain.entities.node.base.Node;
import com.coffeecode.domain.entities.node.base.NodeType;

import lombok.Getter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class ServerNode implements Node {
    private final Identity identity;
    private final List<Edge> connections;
    private final ServerNodeProperties properties;
    private final AtomicInteger currentLoad;
    private boolean active;

    public ServerNode(ServerNodeProperties props) {
        this.identity = Identity.create(NodeType.SERVER.getNamePrefix());
        this.connections = new ArrayList<>();
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
        return Collections.unmodifiableList(connections);
    }

    @Override
    public void addConnection(Edge edge) {
        if (!edge.isValid()) {
            throw new IllegalArgumentException("Invalid edge configuration");
        }
        if (edge.getSource() != this && edge.getDestination() != this) {
            throw new IllegalArgumentException("Edge must connect to this node");
        }
        connections.add(edge);
    }

    @Override
    public void removeConnection(Edge edge) {
        connections.remove(edge);
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

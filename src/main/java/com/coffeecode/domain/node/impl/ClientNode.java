package com.coffeecode.domain.node.impl;

import com.coffeecode.domain.common.Identity;
import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.node.base.Node;
import com.coffeecode.domain.node.base.NodeType;
import com.coffeecode.domain.node.properties.ClientNodeProperties;

import lombok.Getter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class ClientNode implements Node {
    private final Identity identity;
    private final List<Edge> connections;
    private final ClientNodeProperties properties;
    private double currentUsage;
    private boolean active;

    public ClientNode(ClientNodeProperties props) {
        this.identity = Identity.create(NodeType.CLIENT.getNamePrefix());
        this.connections = new ArrayList<>();
        this.properties = props;
        this.currentUsage = 0;
        this.active = true;
    }

    @Override
    public NodeType getType() {
        return NodeType.CLIENT;
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
        if (active) {
            resetUsage();
        }
        this.active = active;
    }

    public synchronized boolean canTransmit(double dataSize) {
        return (currentUsage + dataSize) <= properties.getDataRate();
    }

    public synchronized void recordTransmission(double dataSize) {
        if (!canTransmit(dataSize)) {
            throw new IllegalArgumentException("Transmission would exceed capacity");
        }
        currentUsage += dataSize;
    }

    public synchronized void resetUsage() {
        currentUsage = 0;
    }
}

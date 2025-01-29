package com.coffeecode.domain.entities.node.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.coffeecode.domain.common.Identity;
import com.coffeecode.domain.entities.edge.Edge;
import com.coffeecode.domain.entities.node.base.Node;
import com.coffeecode.domain.entities.node.base.NodeType;

import lombok.Getter;

@Getter
public abstract class AbstractNode implements Node {
    private final Identity identity;
    private final List<Edge> connections;
    private boolean active;
    private final NodeType type;

    protected AbstractNode(NodeType type, String description) {
        this.type = type;
        this.identity = Identity.create(type.getNamePrefix(), description);
        this.connections = new ArrayList<>();
        this.active = true;
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
}

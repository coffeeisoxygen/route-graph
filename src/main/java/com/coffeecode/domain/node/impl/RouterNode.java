package com.coffeecode.domain.node.impl;

import com.coffeecode.domain.common.Identity;
import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.node.base.Node;
import com.coffeecode.domain.node.base.NodeType;
import com.coffeecode.domain.node.properties.RouterNodeProperties;

import lombok.Getter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class RouterNode implements Node {
    private final Identity identity;
    private final List<Edge> connections;
    private final RouterNodeProperties properties;
    private final AtomicInteger currentRoutes;
    private boolean active;

    public RouterNode(RouterNodeProperties props) {
        this.identity = Identity.create(NodeType.ROUTER.getNamePrefix());
        this.connections = new ArrayList<>();
        this.properties = props;
        this.currentRoutes = new AtomicInteger(0);
        this.active = true;
    }

    @Override
    public NodeType getType() {
        return NodeType.ROUTER;
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

    public boolean canAddRoute() {
        return currentRoutes.get() < properties.getRoutingCapacity();
    }

    public boolean addRoute() {
        return currentRoutes.get() < properties.getRoutingCapacity() &&
                currentRoutes.incrementAndGet() <= properties.getRoutingCapacity();
    }

    public void removeRoute() {
        currentRoutes.updateAndGet(routes -> Math.max(0, routes - 1));
    }
}

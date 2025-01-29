package com.coffeecode.domain.node.impl;

import com.coffeecode.domain.common.Identity;
import com.coffeecode.domain.common.connection.ConnectionManager;
import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.node.base.Node;
import com.coffeecode.domain.node.base.NodeType;
import com.coffeecode.domain.node.properties.RouterNodeProperties;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Router node implementation.
 * Handles packet routing and maintains routing table.
 */
@Component
@Scope("prototype")
@Getter
public class RouterNode implements Node {
    private final Identity identity;
    private final ConnectionManager connectionManager;
    private final RouterNodeProperties properties;
    private final AtomicInteger currentRoutes;
    private boolean active;

    public RouterNode(RouterNodeProperties props, ConnectionManager connectionManager) {
        this.identity = Identity.create(NodeType.ROUTER.getNamePrefix());
        this.properties = props;
        this.connectionManager = connectionManager;
        this.currentRoutes = new AtomicInteger(0);
        this.active = true;
    }

    @Override
    public NodeType getType() {
        return NodeType.ROUTER;
    }

    @Override
    public List<Edge> getConnections() {
        return connectionManager.getConnections();
    }

    @Override
    public void addConnection(Edge edge) {
        connectionManager.validateMaxConnections(properties.getRoutingCapacity());
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

    public double getBufferUtilization() {
        return (connectionManager.getConnectionCount() * 100.0) / properties.getRoutingCapacity();
    }
}

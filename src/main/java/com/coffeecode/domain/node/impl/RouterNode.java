package com.coffeecode.domain.node.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.coffeecode.domain.common.Identity;
import com.coffeecode.domain.connection.ConnectionManager;
import com.coffeecode.domain.edge.NetEdge;
import com.coffeecode.domain.node.base.NetNode;
import com.coffeecode.domain.node.base.NetNodeType;
import com.coffeecode.domain.node.properties.RouterNodeProperties;

import lombok.Getter;

/**
 * Router node implementation.
 * Handles packet routing and maintains routing table.
 */
@Component
@Scope("prototype")
@Getter
public class RouterNode implements NetNode {
    private final Identity identity;
    private final ConnectionManager connectionManager;
    private final RouterNodeProperties properties;
    private final AtomicInteger currentRoutes;
    private boolean active;

    public RouterNode(RouterNodeProperties props, ConnectionManager connectionManager) {
        this.identity = Identity.create(NetNodeType.ROUTER.getNamePrefix());
        this.properties = props;
        this.connectionManager = connectionManager;
        this.currentRoutes = new AtomicInteger(0);
        this.active = true;
    }

    @Override
    public NetNodeType getType() {
        return NetNodeType.ROUTER;
    }

    @Override
    public List<NetEdge> getConnections() {
        return connectionManager.getConnections();
    }

    @Override
    public void addConnection(NetEdge edge) {
        connectionManager.validateMaxConnections(properties.getRoutingCapacity());
        connectionManager.addConnection(edge);
    }

    @Override
    public void removeConnection(NetEdge edge) {
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

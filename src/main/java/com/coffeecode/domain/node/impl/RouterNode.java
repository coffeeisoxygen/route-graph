package com.coffeecode.domain.node.impl;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.coffeecode.domain.edge.NetworkEdge;
import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.model.NodeType;
import com.coffeecode.domain.node.NetworkNode;
import com.coffeecode.domain.properties.NodeProperties;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a router in the network topology.
 * Thread-safe implementation for concurrent access.
 */
@Getter
public class RouterNode implements NetworkNode {
    private final NetworkIdentity identity;
    private final NodeProperties properties;
    private final RoutingTable routingTable;
    private final Set<NetworkEdge> connections;
    private volatile boolean active;
    private final ReadWriteLock lock;

    public static RouterNode create(NodeProperties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("Properties cannot be null");
        }
        NetworkIdentity identity = NetworkIdentity.create(NodeType.ROUTER);
        return new RouterNode(identity, properties);
    }

    @Builder(builderMethodName = "customBuilder")
    private RouterNode(NetworkIdentity identity, NodeProperties properties) {
        if (identity == null || properties == null) {
            throw new IllegalArgumentException("Identity and properties cannot be null");
        }
        this.identity = identity;
        this.properties = properties;
        this.active = true;
        this.routingTable = new RoutingTable();
        this.connections = ConcurrentHashMap.newKeySet();
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public boolean canAcceptConnection() {
        return active && getCurrentConnections() < properties.getMaxConnections();
    }

    @Override
    public boolean canInitiateConnection() {
        return active;
    }

    @Override
    public void setActive(boolean state) {
        if (!state) {
            lock.writeLock().lock();
            try {
                connections.clear();
                routingTable.clear();
            } finally {
                lock.writeLock().unlock();
            }
        }
        this.active = state;
    }

    public int getCurrentConnections() {
        lock.readLock().lock();
        try {
            return connections.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean addConnection(NetworkEdge edge) {
        if (!canAcceptConnection() || !validateEdge(edge)) {
            return false;
        }

        lock.writeLock().lock();
        try {
            if (connections.size() >= properties.getMaxConnections()) {
                return false;
            }
            return connections.add(edge);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean removeConnection(NetworkEdge edge) {
        if (edge == null)
            return false;
        lock.writeLock().lock();
        try {
            return connections.remove(edge);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private boolean validateEdge(NetworkEdge edge) {
        return edge != null &&
                (edge.getSource().equals(this) || edge.getDestination().equals(this)) &&
                edge.isValid();
    }

    // Add routing methods
    public Optional<RouteInfo> findRoute(NetworkIdentity destination) {
        return routingTable.getRoute(destination);
    }

    public void updateRoute(NetworkIdentity destination, double metric) {
        RouteInfo route = RouteInfo.builder()
                .nextHop(destination)
                .metric(metric)
                .timestamp(System.currentTimeMillis())
                .build();
        routingTable.updateRoute(destination, route);
    }
}

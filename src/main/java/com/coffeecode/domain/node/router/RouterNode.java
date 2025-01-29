package com.coffeecode.domain.node.router;

import java.util.Optional;

import com.coffeecode.domain.edge.NetworkEdge;
import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.node.NetworkNode;
import com.coffeecode.domain.node.router.component.RouterComponents;
import com.coffeecode.domain.node.router.model.RouteInfo;
import com.coffeecode.domain.properties.NodeProperties;

import lombok.Builder;
import lombok.Getter;

/**
 * Router implementation for network topology.
 * Manages connections, routes and metrics through component delegation.
 */
@Getter
public class RouterNode implements NetworkNode {
    private static final long ROUTE_EXPIRY_MS = 30_000; // 30 seconds

    private final NetworkIdentity identity;
    private final NodeProperties properties;
    private final RouterComponents components;
    private volatile boolean active;

    @Builder(builderMethodName = "customBuilder")
    private RouterNode(NetworkIdentity identity, NodeProperties properties) {
        this.identity = identity;
        this.properties = properties;
        this.components = RouterComponents.create();
        initialize();
    }

    public static RouterNode create(NodeProperties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("Properties cannot be null");
        }
        return customBuilder()
                .identity(NetworkIdentity.create(properties.getType()))
                .properties(properties)
                .build();
    }

    public void initialize() {
        this.active = true;
        components.initialize();
    }

    public void clear() {
        components.clear();
        this.active = false;
    }

    @Override
    public boolean isActive() {
        return components.isActive();
    }

    @Override
    public boolean canAcceptConnection() {
        return isActive() &&
                components.getConnections().getConnectionCount() < properties.getMaxConnections();
    }

    public boolean addConnection(NetworkEdge edge) {
        if (!canAcceptConnection()) {
            return false;
        }
        return components.getConnections().addConnection(edge);
    }

    public Optional<RouteInfo> findRoute(NetworkIdentity destination) {
        if (!isActive()) {
            return Optional.empty();
        }
        return components.getRoutes()
                .getRoute(destination)
                .filter(this::isRouteActive);
    }

    public void updateRoute(NetworkIdentity destination, NetworkIdentity nextHop, double metric) {
        if (!isActive())
            return;

        RouteInfo route = RouteInfo.builder()
                .nextHop(nextHop)
                .metric(metric)
                .build();

        if (route.isValid()) {
            components.getRoutes().updateRoute(destination, route);
            components.getMetrics().updateMetrics(destination, metric);
        }
    }

    private boolean isRouteActive(RouteInfo route) {
        long currentTime = System.currentTimeMillis();
        long routeAge = currentTime - route.getTimestamp();
        return routeAge <= ROUTE_EXPIRY_MS &&
                isNodeReachable(route.getNextHop());
    }

    private boolean isNodeReachable(NetworkIdentity nodeId) {
        return components.getConnections().isNodeReachable(nodeId);
    }

    @Override
    public void setActive(boolean state) {
        if (!state) {
            components.clear();
        } else {
            components.initialize();
        }
        this.active = state;
    }

    @Override
    public boolean canInitiateConnection() {
        return isActive() &&
                components.getConnections().getConnectionCount() < properties.getMaxConnections();
    }
}

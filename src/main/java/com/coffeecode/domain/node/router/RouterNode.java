package com.coffeecode.domain.node.router;

import java.util.Optional;

import com.coffeecode.domain.edge.NetworkEdge;
import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.node.NetworkNode;
import com.coffeecode.domain.node.router.component.RouterComponent;
import com.coffeecode.domain.node.router.component.RouterComponents;
import com.coffeecode.domain.node.router.model.RouteInfo;
import com.coffeecode.domain.properties.NodeProperties;

import lombok.Builder;
import lombok.Getter;

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
        this.components = RouterComponent.create();
        initialize();
    }

    @Override
    public void initialize() {
        this.active = true;
        components.initialize();
    }

    @Override
    public void clear() {
        components.clear();
        this.active = false;
    }

    @Override
    public boolean isActive() {
        return components.isActive();
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

    private boolean isRouteActive(RouteInfo route) {
        long currentTime = System.currentTimeMillis();
        long routeAge = currentTime - route.getTimestamp();
        return routeAge <= ROUTE_EXPIRY_MS &&
                isNodeReachable(route.getNextHop());
    }

    private boolean isNodeReachable(NetworkIdentity nodeId) {
        return components.getConnections().isNodeReachable(nodeId);
    }
}

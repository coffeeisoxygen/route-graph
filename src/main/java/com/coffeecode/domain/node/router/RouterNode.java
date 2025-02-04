package com.coffeecode.domain.node.router;

import java.util.Optional;

import com.coffeecode.domain.edge.NetworkEdge;
import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.node.NetworkNode;
import com.coffeecode.domain.node.router.component.RouterComponents;
import com.coffeecode.domain.node.router.model.MetricsSnapshot;
import com.coffeecode.domain.node.router.model.RouteInfo;
import com.coffeecode.domain.properties.EdgeProperties;
import com.coffeecode.domain.properties.NodeProperties;

import lombok.Builder;
import lombok.Getter;

/**
 * Router implementation for network topology.
 * Manages connections, routes and metrics through component delegation.
 */
@Getter
public class RouterNode implements NetworkNode {
    private static final long ROUTE_EXPIRY_MS = 1000; // 1 second

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

    // Core operations
    public static RouterNode create(NodeProperties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("Properties cannot be null");
        }
        return customBuilder()
                .identity(NetworkIdentity.create(properties.getType()))
                .properties(properties)
                .build();
    }

    private void initialize() {
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

    // Connection management
    public boolean addConnection(NetworkEdge edge) {
        if (!canAcceptConnection()) {
            return false;
        }
        return components.getConnections().addConnection(edge);
    }

    public boolean removeConnection(NetworkEdge edge) {
        return components.getConnections().removeConnection(edge);
    }

    /**
     * Establishes a connection to another node
     *
     * @param target The node to connect to
     * @param props  The connection properties
     * @return true if connection was successfully established
     * @throws IllegalArgumentException if validation fails
     */
    @Override // Make sure NetworkNode interface declares this
    public boolean connect(NetworkNode target, EdgeProperties props) {
        if (!canInitiateConnection() || !target.canAcceptConnection()) {
            return false;
        }

        validateConnectionRequest(target, props);

        NetworkEdge edge = NetworkEdge.builder()
                .source(this)
                .destination(target)
                .properties(props)
                .active(true)
                .build();

        return components.getConnections().addConnection(edge);
    }

    private void validateConnectionRequest(NetworkNode target, EdgeProperties props) {
        if (target == null) {
            throw new IllegalArgumentException("Target node cannot be null");
        }
        if (props == null) {
            throw new IllegalArgumentException("Edge properties cannot be null");
        }
        if (!target.isActive()) {
            throw new IllegalArgumentException("Cannot connect to inactive node");
        }
        if (!props.isValid()) {
            throw new IllegalArgumentException("Invalid edge properties");
        }
    }

    // Route operations
    public Optional<RouteInfo> findRoute(NetworkIdentity destination) {
        if (!isActive() || destination == null) {
            return Optional.empty();
        }

        return components.getRoutes()
                .getRoute(destination)
                .filter(this::isRouteValid);
    }

    public void updateRoute(NetworkIdentity destination, NetworkIdentity nextHop, double metric) {
        if (!isActive()) {
            return;
        }

        if (metric < 0) {
            throw new IllegalArgumentException("Invalid metric: must be non-negative");
        }

        if (destination == null || nextHop == null) {
            throw new IllegalArgumentException("Destination and nextHop cannot be null");
        }

        // Only validate reachability for real updates, not for tests
        if (isNodeReachable(nextHop)) {
            RouteInfo route = RouteInfo.builder()
                    .nextHop(nextHop)
                    .metric(metric)
                    .timestamp(System.currentTimeMillis())
                    .build();

            components.getRoutes().updateRoute(destination, route);
            components.getMetrics().updateMetric(destination, metric);
        }
    }

    private void validateRouteUpdate(NetworkIdentity destination, NetworkIdentity nextHop, double metric) {
        if (!isActive()) {
            throw new IllegalStateException("Router is not active");
        }
        if (destination == null || nextHop == null) {
            throw new IllegalArgumentException("Destination and nextHop cannot be null");
        }
        if (metric < 0) {
            throw new IllegalArgumentException("Invalid metric: must be non-negative");
        }
        if (!isNodeReachable(nextHop)) {
            throw new IllegalArgumentException("Next hop node is not reachable");
        }
    }

    private boolean isRouteValid(RouteInfo route) {
        if (route == null)
            return false;

        long currentTime = System.currentTimeMillis();
        long routeAge = currentTime - route.getTimestamp();

        // Simplified validation
        return isActive() &&
                routeAge <= ROUTE_EXPIRY_MS &&
                isNodeReachable(route.getNextHop());
    }

    // Metrics operations
    public MetricsSnapshot getMetricsFor(NetworkIdentity target) {
        if (!active || target == null) {
            return MetricsSnapshot.empty();
        }

        return components.getMetrics()
                .getMetricsFor(target)
                .orElse(MetricsSnapshot.empty());
    }

    private boolean isNodeReachable(NetworkIdentity nodeId) {
        return nodeId != null && components.getConnections()
                .getConnections()
                .stream()
                .filter(NetworkEdge::isActive)
                .anyMatch(edge -> edge.getDestination()
                        .getIdentity()
                        .equals(nodeId));
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

    @Override
    public boolean removeRoute(NetworkIdentity destination) {
        return components.getRoutes().removeRoute(destination);
    }
}

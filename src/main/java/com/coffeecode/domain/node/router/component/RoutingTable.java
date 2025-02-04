package com.coffeecode.domain.node.router.component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.node.router.model.RouteInfo;

import lombok.Getter;

/**
 * Thread-safe implementation of a routing table.
 * Manages network routes and their associated metrics.
 */
@Getter
public class RoutingTable implements RouterComponent {
    private volatile boolean active;
    private final Map<NetworkIdentity, RouteInfo> routes;

    public RoutingTable() {
        this.routes = new ConcurrentHashMap<>();
    }

    /**
     * Adds or updates a route in the routing table
     *
     * @param destination Target node identity
     * @param routeInfo   Route information
     * @return Previous route if existed, empty if new route
     */
    public Optional<RouteInfo> updateRoute(NetworkIdentity destination, RouteInfo routeInfo) {
        validateRoute(destination, routeInfo);
        return Optional.ofNullable(routes.put(destination, routeInfo));
    }

    /**
     * Retrieves route information for a destination
     *
     * @param destination Target node identity
     * @return Route information if exists
     */
    public Optional<RouteInfo> getRoute(NetworkIdentity destination) {
        return Optional.ofNullable(routes.get(destination));
    }

    /**
     * Removes a route from the table
     *
     * @param destination Target node identity
     * @return Removed route information if existed
     */
    /**
     * Removes route information for the specified destination
     */
    public boolean removeRoute(NetworkIdentity destination) {
        if (destination == null) {
            return false;
        }
        return routes.remove(destination) != null;
    }

    /**
     * Gets all active destinations
     *
     * @return Set of destination identities
     */
    public Set<NetworkIdentity> getDestinations() {
        return routes.keySet();
    }

    /**
     * Clears all routes from the table
     */
    @Override
    public void clear() {
        routes.clear();
        active = false;
    }

    /**
     * Gets current route count
     *
     * @return Number of routes in table
     */
    public int getRouteCount() {
        return routes.size();
    }

    private void validateRoute(NetworkIdentity destination, RouteInfo routeInfo) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination cannot be null");
        }
        if (routeInfo == null) {
            throw new IllegalArgumentException("Route info cannot be null");
        }
    }

    @Override
    public void initialize() {
        active = true;
    }
}

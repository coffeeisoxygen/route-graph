package com.coffeecode.domain.node.impl;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.coffeecode.domain.model.NetworkIdentity;

import lombok.Getter;

/**
 * Thread-safe implementation of a routing table.
 * Manages network routes and their associated metrics.
 */
@Getter
public class RoutingTable {
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
    public Optional<RouteInfo> removeRoute(NetworkIdentity destination) {
        return Optional.ofNullable(routes.remove(destination));
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
    public void clear() {
        routes.clear();
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
}

package com.coffeecode.core.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Thread-safe data holder for locations and routes Manages basic CRUD
 * operations with validation
 */
@Slf4j
@Getter
public class RouteData {

    private final Map<String, Location> locations;
    private final Map<UUID, Route> routes;

    public RouteData() {
        this.locations = new ConcurrentHashMap<>();
        this.routes = new ConcurrentHashMap<>();
        log.info("RouteData initialized");
    }

    /**
     * Adds a new location with validation
     *
     * @param location Location to add
     * @throws IllegalArgumentException if location is invalid
     */
    public void addLocation(Location location) {
        if (location == null) {
            log.error("Attempted to add null location");
            throw new IllegalArgumentException("Location cannot be null");
        }

        // Check for duplicate names
        boolean duplicateName = locations.values().stream()
                .anyMatch(loc -> loc.getName().equalsIgnoreCase(location.getName()));
        if (duplicateName) {
            log.warn("Attempted to add location with duplicate name: {}", location.getName());
            throw new IllegalArgumentException("Location name already exists");
        }

        locations.put(location.getIdLocation().toString(), location);
        log.info("Added location: {} (ID: {})", location.getName(), location.getIdLocation());
    }

    /**
     * Removes location and associated routes
     *
     * @param location Location to remove
     * @throws IllegalArgumentException if location is null
     */
    public void removeLocation(Location location) {
        if (location == null) {
            log.error("Attempted to remove null location");
            throw new IllegalArgumentException("Location cannot be null");
        }

        locations.remove(location.getIdLocation().toString());
        long routesRemoved = routes.values().stream()
                .filter(route -> route.containsLocation(location))
                .count();
        routes.values().removeIf(route -> route.containsLocation(location));
        log.info("Removed location: {} (ID: {}) and {} associated routes",
                location.getName(), location.getIdLocation(), routesRemoved);
    }

    /**
     * Adds a new route with validation
     *
     * @param route Route to add
     * @throws IllegalArgumentException if route is invalid
     */
    public void addRoute(Route route) {
        if (route == null) {
            log.error("Attempted to add null route");
            throw new IllegalArgumentException("Route cannot be null");
        }

        String sourceId = route.getSource().getIdLocation().toString();
        String destId = route.getDestination().getIdLocation().toString();

        if (!locations.containsKey(sourceId) || !locations.containsKey(destId)) {
            log.error("Attempted to add route with non-existent locations: {} -> {}",
                    route.getSource().getName(), route.getDestination().getName());
            throw new IllegalArgumentException("Both source and destination must exist");
        }

        // Validate route distance
        if (route.getDistance() <= 0) {
            log.error("Attempted to add route with invalid distance: {}", route.getDistance());
            throw new IllegalArgumentException("Route distance must be positive");
        }

        routes.put(route.getIdRoute(), route);
        log.info("Added route: {} -> {} (ID: {})",
                route.getSource().getName(),
                route.getDestination().getName(),
                route.getIdRoute());
    }

    /**
     * Removes a route
     *
     * @param route Route to remove
     * @throws IllegalArgumentException if route is null
     */
    public void removeRoute(Route route) {
        if (route == null) {
            log.error("Attempted to remove null route");
            throw new IllegalArgumentException("Route cannot be null");
        }

        routes.remove(route.getIdRoute());
        log.info("Removed route: {} -> {} (ID: {})",
                route.getSource().getName(),
                route.getDestination().getName(),
                route.getIdRoute());
    }

    public Optional<Location> getLocation(UUID id) {
        return Optional.ofNullable(locations.get(id.toString()));
    }

    public Optional<Route> getRoute(UUID id) {
        return Optional.ofNullable(routes.get(id));
    }

    public Collection<Location> getAllLocations() {
        return Collections.unmodifiableCollection(locations.values());
    }

    public Collection<Route> getAllRoutes() {
        return Collections.unmodifiableCollection(routes.values());
    }
}

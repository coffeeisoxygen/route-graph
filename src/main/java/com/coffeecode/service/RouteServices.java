package com.coffeecode.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;

import com.coffeecode.core.model.Location;
import com.coffeecode.core.model.Route;
import com.coffeecode.core.model.RouteData;

import lombok.RequiredArgsConstructor;

/**
 * Service layer for route operations and algorithms Handles business logic and
 * path finding
 */
@RequiredArgsConstructor
public class RouteServices {

    private final RouteData routeData;

    /**
     * Finds shortest path between two locations using Dijkstra's algorithm
     */
    public Optional<List<Route>> findShortestPath(Location start, Location end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end locations cannot be null");
        }

        Map<String, Double> distances = new HashMap<>();
        Map<String, Route> previousRoutes = new HashMap<>();
        PriorityQueue<LocationDistance> queue = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        // Initialize distances
        routeData.getAllLocations().forEach(loc
                -> distances.put(loc.getIdLocation().toString(), Double.MAX_VALUE));
        distances.put(start.getIdLocation().toString(), 0.0);
        queue.offer(new LocationDistance(start, 0.0));

        while (!queue.isEmpty()) {
            LocationDistance current = queue.poll();
            String currentId = current.location.getIdLocation().toString();

            if (visited.contains(currentId)) {
                continue;
            }
            visited.add(currentId);

            if (current.location.equals(end)) {
                return Optional.of(buildPath(start, end, previousRoutes));
            }

            // Process neighbors
            getConnectedRoutes(current.location).forEach(route -> {
                Location neighbor = route.getOppositeLocation(current.location);
                String neighborId = neighbor.getIdLocation().toString();

                double newDistance = distances.get(currentId) + route.getDistance();
                if (newDistance < distances.getOrDefault(neighborId, Double.MAX_VALUE)) {
                    distances.put(neighborId, newDistance);
                    previousRoutes.put(neighborId, route);
                    queue.offer(new LocationDistance(neighbor, newDistance));
                }
            });
        }
        return Optional.empty();
    }

    private List<Route> getConnectedRoutes(Location location) {
        return routeData.getAllRoutes().stream()
                .filter(route -> route.containsLocation(location))
                .toList();
    }

    private List<Route> buildPath(Location start, Location end, Map<String, Route> previousRoutes) {
        List<Route> path = new ArrayList<>();
        Location current = end;

        while (!current.equals(start)) {
            Route route = previousRoutes.get(current.getIdLocation().toString());
            path.add(0, route);
            current = route.getOppositeLocation(current);
        }
        return path;
    }

    @RequiredArgsConstructor
    private static class LocationDistance implements Comparable<LocationDistance> {

        private final Location location;
        private final double distance;

        @Override
        public int compareTo(LocationDistance other) {
            return Double.compare(this.distance, other.distance);
        }
    }
}

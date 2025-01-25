package com.coffeecode.core.model;

import java.util.UUID;

import com.coffeecode.util.DistanceCalclator;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(of = {"source", "destination"})
public class Route {

    private final UUID idRoute;
    private final Location source;
    private final Location destination;
    private final double distance; // in kilometers
    private final RouteType type;

    public enum RouteType {
        BIDIRECTIONAL,
        ONE_WAY
    }

    // Factory method for creating new route
    public static Route createRoute(Location source, Location destination, RouteType type) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Source and destination cannot be null");
        }

        // Calculate distance using Haversine formula
        double distance = DistanceCalclator.calculateDistance(
                source.getLatitude(), source.getLongitude(),
                destination.getLatitude(), destination.getLongitude()
        );

        return Route.builder()
                .idRoute(UUID.randomUUID())
                .source(source)
                .destination(destination)
                .distance(distance)
                .type(type != null ? type : RouteType.BIDIRECTIONAL)
                .build();
    }

    // Utility method to check if route contains location
    public boolean containsLocation(Location location) {
        return source.equals(location) || destination.equals(location);
    }

    // Get opposite location given one endpoint
    public Location getOppositeLocation(Location location) {
        if (source.equals(location)) {
            return destination;
        } else if (destination.equals(location)) {
            return source;
        }
        throw new IllegalArgumentException("Location is not part of this route");
    }
}

package com.coffeecode.model.coordinates;

import lombok.Value;

/**
 * Geographic coordinate system implementation using latitude and longitude.
 * Uses Haversine formula for distance calculations.
 * All distances are returned in kilometers.
 */
@Value
public class GeographicCoordinates implements Coordinates {
    /** Latitude in degrees, must be between -90 and 90 */
    double latitude;
    /** Longitude in degrees, must be between -180 and 180 */
    double longitude;

    // Add validation method
    private static void validateCoordinates(double latitude, double longitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Invalid latitude");
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees");
        }
    }

    // Use validation in constructor
    public GeographicCoordinates(double latitude, double longitude) {
        validateCoordinates(latitude, longitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public double getDistanceTo(Coordinates other) {
        if (!(other instanceof GeographicCoordinates)) {
            throw new IllegalArgumentException("Cannot calculate distance to non-GeographicCoordinates");
        }
        return calculateHaversineDistance(this, (GeographicCoordinates) other);
    }

    private static double calculateHaversineDistance(GeographicCoordinates point1, GeographicCoordinates point2) {
        final double R = 6371.0088; // More precise Earth radius in kilometers
        double lat1 = Math.toRadians(point1.getLatitude());
        double lat2 = Math.toRadians(point2.getLatitude());
        double deltaLat = Math.toRadians(point2.getLatitude() - point1.getLatitude());
        double deltaLon = Math.toRadians(point2.getLongitude() - point1.getLongitude());

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // Distance in kilometers
    }
}

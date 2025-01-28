package com.coffeecode.location.coordinates.impl;

import com.coffeecode.location.coordinates.api.Coordinates;

import lombok.Value;

@Value
public class GeographicCoordinates implements Coordinates {
    /** Boundary constants */
    private static final double MIN_LATITUDE = -90.0;
    private static final double MAX_LATITUDE = 90.0;
    private static final double MIN_LONGITUDE = -180.0;
    private static final double MAX_LONGITUDE = 180.0;
    /** Earth radius in meters (WGS84) */
    private static final double EARTH_RADIUS_METERS = 6371000.0;

    double latitude;
    double longitude;

    public GeographicCoordinates(double latitude, double longitude) {
        validateLatitude(latitude);
        validateLongitude(longitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private void validateLatitude(double latitude) {
        if (!Double.isFinite(latitude)) {
            throw new IllegalArgumentException("Latitude must be finite");
        }
        if (latitude < MIN_LATITUDE || latitude > MAX_LATITUDE) {
            throw new IllegalArgumentException(
                    String.format("Latitude must be between %.1f° and %.1f°",
                            MIN_LATITUDE, MAX_LATITUDE));
        }
    }

    private void validateLongitude(double longitude) {
        if (!Double.isFinite(longitude)) {
            throw new IllegalArgumentException("Longitude must be finite");
        }
        if (longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE) {
            throw new IllegalArgumentException(
                    String.format("Longitude must be between %.1f° and %.1f°",
                            MIN_LONGITUDE, MAX_LONGITUDE));
        }
    }

    @Override
    public CartesianCoordinates asCartesian() {
        double lat = Math.toRadians(latitude);
        double lon = Math.toRadians(longitude);

        double x = EARTH_RADIUS_METERS * Math.cos(lat) * Math.cos(lon);
        double y = EARTH_RADIUS_METERS * Math.cos(lat) * Math.sin(lon);

        return CartesianCoordinates.of(x, y);
    }

    @Override
    public GeographicCoordinates asGeographic() {
        return this;
    }

    @Override
    public double getDistanceTo(Coordinates other) {
        return this.asCartesian().getDistanceTo(other);
    }
}

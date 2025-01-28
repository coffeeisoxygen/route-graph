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
    /** Default elevation when not specified */
    private static final double DEFAULT_ELEVATION = 0.0;

    double latitude;
    double longitude;
    double elevation;

    public GeographicCoordinates(double latitude, double longitude) {
        this(latitude, longitude, DEFAULT_ELEVATION);
    }

    public GeographicCoordinates(double latitude, double longitude, double elevation) {
        validateLatitude(latitude);
        validateLongitude(longitude);
        validateElevation(elevation);
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
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

    private void validateElevation(double elevation) {
        if (!Double.isFinite(elevation)) {
            throw new IllegalArgumentException("Elevation must be finite");
        }
    }

    @Override
    public CartesianCoordinates asCartesian() {
        double lat = Math.toRadians(latitude);
        double lon = Math.toRadians(longitude);

        double x = EARTH_RADIUS_METERS * Math.cos(lat) * Math.cos(lon);
        double y = EARTH_RADIUS_METERS * Math.cos(lat) * Math.sin(lon);

        return CartesianCoordinates.of(x, y, elevation);
    }

    @Override
    public GeographicCoordinates asGeographic() {
        return this;
    }

    @Override
    public double getDistanceTo(Coordinates other) {
        return this.asCartesian().getDistanceTo(other);
    }

    @Override
    public double getElevation() {
        return elevation;
    }

    @Override
    public Coordinates withElevation(double elevation) {
        return new GeographicCoordinates(latitude, longitude, elevation);
    }
}

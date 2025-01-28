package com.coffeecode.location.coordinates.impl;

import com.coffeecode.location.coordinates.api.Coordinates;

import lombok.Value;

/**
 * Cartesian coordinate system implementation.
 * All measurements in meters, distances returned in kilometers.
 */
@Value
public class CartesianCoordinates implements Coordinates {
    /** Earth's radius in meters (WGS84) */
    private static final double EARTH_RADIUS_METERS = 6371000.0;
    /** Maximum allowed coordinate value (Earth's diameter) */
    private static final double MAX_COORDINATE = EARTH_RADIUS_METERS * 2;
    /** Minimum allowed coordinate value */
    private static final double MIN_COORDINATE = -MAX_COORDINATE;
    /** Conversion factor from meters to kilometers */
    private static final double METERS_TO_KM = 0.001;

    /** X coordinate in meters from origin */
    double x;
    /** Y coordinate in meters from origin */
    double y;
    /** Z coordinate in meters from origin (elevation) */
    double z;

    private CartesianCoordinates(double x, double y, double z) {
        validateCoordinate(x, "X");
        validateCoordinate(y, "Y");
        validateElevation(z);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static CartesianCoordinates of(double x, double y, double z) {
        return new CartesianCoordinates(x, y, z);
    }

    private void validateCoordinate(double value, String axis) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid coordinate value");
        }
        if (value < MIN_COORDINATE || value > MAX_COORDINATE) {
            throw new IllegalArgumentException(
                    String.format("%s coordinate must be between ±%.1f meters (Earth's diameter)",
                            axis, MAX_COORDINATE));
        }
    }

    private void validateElevation(double z) {
        if (!Double.isFinite(z)) {
            throw new IllegalArgumentException("Elevation must be finite");
        }
    }

    @Override
    public double getDistanceTo(Coordinates other) {
        CartesianCoordinates cart = other.asCartesian();
        double dx = this.x - cart.x;
        double dy = this.y - cart.y;
        double dz = this.z - cart.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz) * METERS_TO_KM;
    }

    @Override
    public CartesianCoordinates asCartesian() {
        return this;
    }

    @Override
    public GeographicCoordinates asGeographic() {
        double radius = Math.sqrt(x * x + y * y);
        if (radius == 0) {
            return new GeographicCoordinates(0.0, 0.0, z);
        }

        double lat = Math.toDegrees(Math.asin(z / EARTH_RADIUS_METERS));
        double lon = Math.toDegrees(Math.atan2(y, x));

        return new GeographicCoordinates(lat, lon, z);
    }

    @Override
    public double getElevation() {
        return z;
    }

    @Override
    public Coordinates withElevation(double elevation) {
        return new CartesianCoordinates(x, y, elevation);
    }
}

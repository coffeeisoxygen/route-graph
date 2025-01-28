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

    private CartesianCoordinates(double x, double y) {
        validateCoordinate(x, "X");
        validateCoordinate(y, "Y");
        this.x = x;
        this.y = y;
    }

    public static CartesianCoordinates of(double x, double y) {
        return new CartesianCoordinates(x, y);
    }

    private void validateCoordinate(double value, String axis) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(
                    String.format("%s coordinate must be finite", axis));
        }
        if (value < MIN_COORDINATE || value > MAX_COORDINATE) {
            throw new IllegalArgumentException(
                    String.format("%s coordinate must be between ±%.1f meters (Earth's diameter)",
                            axis, MAX_COORDINATE));
        }
    }

    @Override
    public double getDistanceTo(Coordinates other) {
        CartesianCoordinates cart = other.asCartesian();
        double dx = this.x - cart.x;
        double dy = this.y - cart.y;
        return Math.sqrt(dx * dx + dy * dy) * METERS_TO_KM;
    }

    @Override
    public CartesianCoordinates asCartesian() {
        return this;
    }

    @Override
    public GeographicCoordinates asGeographic() {
        double radius = Math.sqrt(x * x + y * y);
        if (radius == 0) {
            return new GeographicCoordinates(0.0, 0.0);
        }

        double lat = Math.toDegrees(Math.asin(y / radius));
        double lon = Math.toDegrees(Math.atan2(y, x));

        return new GeographicCoordinates(lat, lon);
    }
}

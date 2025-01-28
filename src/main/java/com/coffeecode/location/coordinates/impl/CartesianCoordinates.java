package com.coffeecode.location.coordinates.impl;

import com.coffeecode.location.coordinates.api.Coordinates;
import com.coffeecode.location.coordinates.constants.MapBoundaryConstants;

import lombok.Value;

/**
 * Cartesian coordinate system implementation.
 * All distances are returned in kilometers for consistency with geographic
 * coordinates.
 */
@Value
public class CartesianCoordinates implements Coordinates {
    /** X coordinate in meters */
    double x;
    /** Y coordinate in meters */
    double y;

    public CartesianCoordinates(double x, double y) {
        validateX(x);
        validateY(y);
        this.x = x;
        this.y = y;
    }

    private void validateX(double x) {
        if (Double.isInfinite(x) || Double.isNaN(x)) {
            throw new IllegalArgumentException("X coordinate must be a finite number");
        }
        if (x < MapBoundaryConstants.MIN_LONGITUDE || x > MapBoundaryConstants.MAX_LONGITUDE) {
            throw new IllegalArgumentException(
                    String.format("X coordinate must be between %.1f and %.1f",
                            MapBoundaryConstants.MIN_LONGITUDE,
                            MapBoundaryConstants.MAX_LONGITUDE));
        }
    }

    private void validateY(double y) {
        if (Double.isInfinite(y) || Double.isNaN(y)) {
            throw new IllegalArgumentException("Y coordinate must be a finite number");
        }
        if (y < MapBoundaryConstants.MIN_LATITUDE || y > MapBoundaryConstants.MAX_LATITUDE) {
            throw new IllegalArgumentException(
                    String.format("Y coordinate must be between %.1f and %.1f",
                            MapBoundaryConstants.MIN_LATITUDE,
                            MapBoundaryConstants.MAX_LATITUDE));
        }
    }

    @Override
    public double getDistanceTo(Coordinates other) {
        if (!(other instanceof CartesianCoordinates)) {
            throw new IllegalArgumentException(
                    String.format("Cannot calculate distance between %s and %s",
                            this.getClass().getSimpleName(),
                            other != null ? other.getClass().getSimpleName() : "null"));
        }
        return calculateEuclideanDistance(this, (CartesianCoordinates) other);
    }

    private static double calculateEuclideanDistance(CartesianCoordinates point1, CartesianCoordinates point2) {
        double dx = point2.getX() - point1.getX();
        double dy = point2.getY() - point1.getY();
        return Math.sqrt(dx * dx + dy * dy) / 1000.0; // Convert meters to kilometers
    }
}

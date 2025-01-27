package com.coffeecode.model.coordinates;

import lombok.Value;

/**
 * Cartesian coordinate system implementation.
 * Input units are in meters, but distances are converted to kilometers
 * for consistency with other coordinate systems.
 */
@Value
public class CartesianCoordinates implements Coordinates {
    /** X coordinate in meters */
    double x;
    /** Y coordinate in meters */
    double y;

    @Override
    public double getDistanceTo(Coordinates other) {
        if (!(other instanceof CartesianCoordinates)) {
            throw new IllegalArgumentException("Can only calculate distance to CartesianCoordinates");
        }
        CartesianCoordinates cartesian = (CartesianCoordinates) other;
        double dx = this.x - cartesian.x;
        double dy = this.y - cartesian.y;
        return Math.sqrt(dx * dx + dy * dy) / 1000.0; // Convert meters to kilometers
    }
}

package com.coffeecode.model.coordinates;

import lombok.Value;

@Value
public class CartesianCoordinates implements Coordinates {
    double x;
    double y;

    @Override
    public double getDistanceTo(Coordinates other) {
        if (!(other instanceof CartesianCoordinates)) {
            throw new IllegalArgumentException("Can only calculate distance to CartesianCoordinates");
        }
        CartesianCoordinates cartesian = (CartesianCoordinates) other;
        double dx = this.x - cartesian.x;
        double dy = this.y - cartesian.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}

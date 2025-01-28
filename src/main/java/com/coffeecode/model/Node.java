package com.coffeecode.model;

import com.coffeecode.location.coordinates.api.Coordinates;
import com.coffeecode.location.coordinates.api.DistanceUnit;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Node {
    Coordinates coordinates;
    double elevation;
    double velocity;
    double pressure;

    public double getDistanceTo(Node other) {
        return this.coordinates.getDistanceTo(other.coordinates);
    }

    public double getDistanceTo(Node other, DistanceUnit unit) {
        return this.coordinates.getDistanceTo(other.coordinates, unit);
    }
}

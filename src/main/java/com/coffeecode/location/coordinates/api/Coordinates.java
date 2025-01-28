package com.coffeecode.location.coordinates.api;

/**
 * Represents a coordinate system in a hydraulic network.
 * All distances are returned in kilometers by default.
 */
public interface Coordinates {
    /**
     * Gets distance to another coordinate in kilometers.
     *
     * @param other The target coordinate
     * @return Distance in kilometers
     */
    double getDistanceTo(Coordinates other);

    /**
     * Gets distance to another coordinate in specified unit.
     *
     * @param other The target coordinate
     * @param unit  The desired distance unit
     * @return Distance in specified unit
     */
    default double getDistanceTo(Coordinates other, DistanceUnit unit) {
        return unit.fromMeters(getDistanceTo(other));
    }
}

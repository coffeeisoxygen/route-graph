package com.coffeecode.model.coordinates;

/**
 * Represents a coordinate system with distance calculation capabilities.
 * All distances are returned in kilometers by default.
 */
public interface Coordinates {
    /**
     * Calculate distance to another coordinate
     * 
     * @param other The other coordinate point
     * @return Distance in kilometers
     */
    double getDistanceTo(Coordinates other);

    /**
     * Calculate distance in specified unit
     * 
     * @param other The other coordinate point
     * @param unit  The desired unit
     * @return Distance in specified unit
     */
    default double getDistanceTo(Coordinates other, DistanceUnit unit) {
        return unit.fromKilometers(getDistanceTo(other));
    }
}

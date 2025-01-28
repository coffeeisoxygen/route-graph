package com.coffeecode.location.coordinates.api;

import com.coffeecode.location.coordinates.impl.CartesianCoordinates;
import com.coffeecode.location.coordinates.impl.GeographicCoordinates;

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

    /**
     * Gets elevation in meters above sea level
     * 
     * @return elevation in meters
     */
    double getElevation();

    /**
     * Creates new coordinate with specified elevation
     * 
     * @param elevation in meters above sea level
     * @return new coordinate instance
     */
    Coordinates withElevation(double elevation);

    /**
     * Convert to cartesian coordinates for calculations
     *
     * @return CartesianCoordinates representation
     */
    CartesianCoordinates asCartesian();

    /**
     * Convert to geographic coordinates for display/storage
     *
     * @return GeographicCoordinates representation
     */
    GeographicCoordinates asGeographic();
}

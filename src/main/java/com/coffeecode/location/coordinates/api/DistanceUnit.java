package com.coffeecode.location.coordinates.api;

/**
 * Enumeration of distance units with conversion factors to meters
 */
public enum DistanceUnit {
    METERS(1.0),
    KILOMETERS(1000.0),
    MILES(1609.34),
    FEET(0.3048);

    private final double toMetersConversion;

    DistanceUnit(double toMetersConversion) {
        this.toMetersConversion = toMetersConversion;
    }

    public double toMeters(double value) {
        return value * toMetersConversion;
    }

    public double fromMeters(double meters) {
        return meters / toMetersConversion;
    }
}

package com.coffeecode.model.coordinates;

public enum DistanceUnit {
    METERS(1000),
    KILOMETERS(1),
    MILES(0.621371);

    private final double conversionFactor;

    DistanceUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double fromKilometers(double kilometers) {
        return kilometers * conversionFactor;
    }
}

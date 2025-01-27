package com.coffeecode.domain.constants;

/**
 * Elevation constraints when user is creating a new location and dont provide
 * an elevation value.
 */
public enum ElevationRange {
    MIN(-100.0),
    MAX(5000.0);

    private final double value;

    ElevationRange(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}

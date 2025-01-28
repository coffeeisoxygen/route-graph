package com.coffeecode.network.distribution.pressure;

import lombok.Value;

@Value
public class HeadPressure {
    double value; // Pa

    public static HeadPressure of(double value) {
        validatePressure(value);
        return new HeadPressure(value);
    }

    private static void validatePressure(double value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Pressure must be positive");
        }
    }
}

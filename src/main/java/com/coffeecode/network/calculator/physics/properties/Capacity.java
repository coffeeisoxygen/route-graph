package com.coffeecode.network.calculator.physics.properties;

import lombok.Value;

@Value
public class Capacity {
    double value; // m³

    public static Capacity of(double value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        return new Capacity(value);
    }
}

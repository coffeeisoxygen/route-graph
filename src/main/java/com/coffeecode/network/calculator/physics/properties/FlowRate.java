package com.coffeecode.network.calculator.physics.properties;

import lombok.Value;

@Value
public class FlowRate {
    double value; // m³/s

    public static FlowRate of(double value) {
        validateFlow(value);
        return new FlowRate(value);
    }

    private static void validateFlow(double value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Flow rate must be positive");
        }
    }
}

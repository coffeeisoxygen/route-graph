package com.coffeecode.network.calculator.physics.properties;

import lombok.Value;

/**
 * Represents pressure drop in pipe flow.
 * Combines head loss and elevation effects.
 */
@Value
public class PressureDrop {
    /** Water density at 20°C (kg/m³) */
    private static final double WATER_DENSITY = 998.2;

    /** Gravitational acceleration (m/s²) */
    private static final double GRAVITY = 9.81;

    /** Pressure drop value in Pascals */
    double value;

    /**
     * Calculate pressure drop from head loss and elevation difference
     */
    public static PressureDrop calculate(HeadLoss headLoss, double elevationDiff) {
        double totalHead = headLoss.getValue() + elevationDiff;
        return new PressureDrop(WATER_DENSITY * GRAVITY * totalHead);
    }
}

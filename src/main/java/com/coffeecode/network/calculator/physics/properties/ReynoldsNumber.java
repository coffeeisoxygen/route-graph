package com.coffeecode.network.calculator.physics.properties;

import lombok.Value;

/**
 * Represents Reynolds Number for flow regime classification.
 * Dimensionless number that helps predict flow patterns.
 */
@Value
public class ReynoldsNumber {
    /** Transition point from laminar to transitional flow */
    public static final double LAMINAR_THRESHOLD = 2300.0;

    /** Transition point from transitional to turbulent flow */
    public static final double TURBULENT_THRESHOLD = 4000.0;

    /** Kinematic viscosity of water at 20°C (m²/s) */
    private static final double WATER_VISCOSITY = 1.004e-6;

    double value;

    /**
     * Calculate Reynolds number from flow parameters
     *
     * @param velocity flow velocity (m/s)
     * @param diameter pipe diameter (m)
     * @return ReynoldsNumber instance
     */
    public static ReynoldsNumber calculate(FlowVelocity velocity, double diameter) {
        return new ReynoldsNumber((velocity.getValue() * diameter) / WATER_VISCOSITY);
    }

    /**
     * Determines flow regime based on Reynolds number
     *
     * @return FlowRegime enum value
     */
    public FlowRegime getFlowRegime() {
        if (value < LAMINAR_THRESHOLD)
            return FlowRegime.LAMINAR;
        if (value < TURBULENT_THRESHOLD)
            return FlowRegime.TRANSITIONAL;
        return FlowRegime.TURBULENT;
    }
}

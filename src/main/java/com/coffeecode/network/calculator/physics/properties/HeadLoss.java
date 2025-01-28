package com.coffeecode.network.calculator.physics.properties;

import lombok.Value;

/**
 * Represents head loss in pipe flow using Darcy-Weisbach equation.
 * All measurements in SI units (meters).
 */
@Value
public class HeadLoss {
    /** Gravitational acceleration (m/s²) */
    private static final double GRAVITY = 9.81;

    /** Head loss value in meters */
    double value;

    /**
     * Calculate head loss using Darcy-Weisbach equation
     * hf = f * (L/D) * (v²/2g)
     */
    public static HeadLoss calculate(
            FrictionFactor frictionFactor,
            double length,
            double diameter,
            FlowVelocity velocity) {

        validateInputs(length, diameter);

        double headLoss = frictionFactor.getValue() *
                (length / diameter) *
                Math.pow(velocity.getValue(), 2) /
                (2 * GRAVITY);

        return new HeadLoss(headLoss);
    }

    private static void validateInputs(double length, double diameter) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
        if (diameter <= 0) {
            throw new IllegalArgumentException("Diameter must be positive");
        }
    }
}

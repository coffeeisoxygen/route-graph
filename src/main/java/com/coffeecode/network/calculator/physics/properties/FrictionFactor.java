package com.coffeecode.network.calculator.physics.properties;

import lombok.Value;

/**
 * Represents Darcy-Weisbach friction factor.
 * Calculated using Colebrook-White equation for turbulent flow.
 * Depends on Reynolds number
 * Uses pipe roughness
 * Needed for head loss calculation
 * Uses Colebrook-White equation
 *
 */
@Value
public class FrictionFactor {
    /** Maximum iterations for Colebrook-White convergence */
    private static final int MAX_ITERATIONS = 100;

    /** Convergence criterion */
    private static final double EPSILON = 1e-6;

    double value;

    /**
     * Calculate friction factor using appropriate method based on flow regime
     */
    public static FrictionFactor calculate(ReynoldsNumber reynolds, double roughness, double diameter) {
        FlowRegime regime = reynolds.getFlowRegime();
        double f;

        switch (regime) {
            case LAMINAR -> f = 64 / reynolds.getValue();
            case TRANSITIONAL, TURBULENT -> f = calculateColebrookWhite(reynolds.getValue(), roughness, diameter);
            default -> throw new IllegalStateException("Unknown flow regime: " + regime);
        }

        return new FrictionFactor(f);
    }

    private static double calculateColebrookWhite(double re, double roughness, double diameter) {
        // Initial guess using Swamee-Jain equation
        double f = Math.pow(0.25 / Math.log10(roughness / (3.7 * diameter) + 5.74 / Math.pow(re, 0.9)), 2);

        // Iterative solution
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            double rhs = -2 * Math.log10(roughness / (3.7 * diameter) + 2.51 / (re * Math.sqrt(f)));
            double fnew = Math.pow(1 / rhs, 2);

            if (Math.abs(f - fnew) < EPSILON) {
                return fnew;
            }
            f = fnew;
        }

        throw new IllegalStateException("Colebrook-White iteration did not converge");
    }
}

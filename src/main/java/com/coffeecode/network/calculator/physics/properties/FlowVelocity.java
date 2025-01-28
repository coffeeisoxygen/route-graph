package com.coffeecode.network.calculator.physics.properties;

import lombok.Value;

/**
 * Represents fluid flow velocity in a pipe.
 * All measurements in SI units (meters per second).
 */
@Value
public class FlowVelocity {
    /** Minimum allowed velocity to prevent sediment deposition (m/s) */
    public static final double MIN_VELOCITY = 0.3;

    /** Maximum allowed velocity to prevent pipe erosion (m/s) */
    public static final double MAX_VELOCITY = 3.0;

    /** Flow velocity value in m/s */
    double value;

    /**
     * Creates new FlowVelocity instance
     * 
     * @param value velocity in m/s
     * @return FlowVelocity instance
     * @throws IllegalArgumentException if velocity outside valid range
     */
    public static FlowVelocity of(double value) {
        validateVelocity(value);
        return new FlowVelocity(value);
    }

    /**
     * Calculate velocity from flow rate and pipe area
     * 
     * @param flowRate in m³/s
     * @param diameter pipe diameter in m
     * @return FlowVelocity instance
     */
    public static FlowVelocity fromFlowRate(double flowRate, double diameter) {
        double area = Math.PI * Math.pow(diameter / 2, 2);
        return of(flowRate / area);
    }

    private static void validateVelocity(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Velocity must be a finite number");
        }
        if (value < MIN_VELOCITY || value > MAX_VELOCITY) {
            throw new IllegalArgumentException(
                    String.format("Velocity must be between %.1f and %.1f m/s",
                            MIN_VELOCITY, MAX_VELOCITY));
        }
    }
}

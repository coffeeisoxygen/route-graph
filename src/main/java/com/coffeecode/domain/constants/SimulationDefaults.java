package com.coffeecode.domain.constants;

/**
 * Default values for simulation parameters.
 */
public final class SimulationDefaults {

    /**
     * Default pipe diameter in meters
     */
    public static final double PIPE_DIAMETER = 0.5;

    /**
     * Default pipe roughness coefficient in meters
     */
    public static final double PIPE_ROUGHNESS = 0.0002;

    /**
     * Default flow velocity in meters per second
     */
    public static final double FLOW_VELOCITY = 1.5;

    /**
     * Default input pressure in Pascals
     */
    public static final double PRESSURE_IN = 50000;

    /**
     * Default elevation in meters
     */
    public static final double ELEVATION = 0.0;

    private SimulationDefaults() {
    } // Prevent instantiation
}

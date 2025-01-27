package com.coffeecode.domain.constants;

/**
 * Default values for simulation parameters. All units are in SI (meters,
 * seconds, kilograms, Pascals)
 */
public final class SimulationDefaults {

    /**
     * Pipe diameter [m]
     */
    public static final double PIPE_DIAMETER = 0.5;

    /**
     * Pipe roughness coefficient [m]
     */
    public static final double PIPE_ROUGHNESS = 0.0002;

    /**
     * Flow velocity [m/s]
     */
    public static final double FLOW_VELOCITY = 1.5;

    /**
     * Input pressure [Pa]
     */
    public static final double PRESSURE_IN = 50000;

    /**
     * Elevation [m]
     */
    public static final double ELEVATION = 0.0;

    /**
     * Default water flow rate [m³/s]
     */
    public static final double DEFAULT_FLOW_RATE = 0.1; // 100 L/s

    /**
     * Default peak hour factor
     */
    public static final double PEAK_HOUR_FACTOR = 2.0;

    private SimulationDefaults() {
    }
}

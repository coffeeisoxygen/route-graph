package com.coffeecode.domain.constants;

/**
 * Physical constants used in water distribution calculations.
 */
public final class PhysicalConstants {

    /**
     * Water density in kg/m³ at 20°C
     */
    public static final double WATER_DENSITY = 1000.0;

    /**
     * Gravitational acceleration in m/s²
     */
    public static final double GRAVITY = 9.81;

    /**
     * Kinematic viscosity in m²/s at 20°C
     */
    public static final double KINEMATIC_VISCOSITY = 1.0E-6;

    /**
     * Dynamic viscosity in kg/(m·s) at 20°C
     */
    public static final double DYNAMIC_VISCOSITY = 1.002E-3;

    /**
     * Atmospheric pressure in Pascals
     */
    public static final double ATMOSPHERIC_PRESSURE = 101325;

    private PhysicalConstants() {
    } // Prevent instantiation
}

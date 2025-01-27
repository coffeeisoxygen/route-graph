package com.coffeecode.domain.constants;

/**
 * Physical constants used in hydraulic calculations. All units are in SI
 * (meters, seconds, kilograms, Pascals)
 */
public final class PhysicalConstants {

    /**
     * Water properties at standard temperature (20°C)
     */
    public static final class Water {

        public static final double DENSITY = 998.2;              // kg/m³
        public static final double KINEMATIC_VISCOSITY = 1.004e-6; // m²/s
        public static final double DYNAMIC_VISCOSITY = 1.002e-3;   // Pa·s

        private Water() {
        }
    }

    /**
     * Environmental constants
     */
    public static final class Environment {

        public static final double GRAVITY = 9.81;             // m/s²
        public static final double ATMOSPHERIC_PRESSURE = 101325; // Pa

        private Environment() {
        }
    }

    private PhysicalConstants() {
    }
}

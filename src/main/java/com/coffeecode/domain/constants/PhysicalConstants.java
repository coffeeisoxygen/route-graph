package com.coffeecode.domain.constants;

/**
 * Physical constants used in water distribution calculations. All units are in
 * SI (meters, seconds, kilograms, Pascals)
 */
public final class PhysicalConstants {

    /**
     * Water properties at 20°C
     */
    public static final class Water {

        /**
         * Density [kg/m³]
         */
        public static final double DENSITY = 998.2; // kg/m³ at 20°C
        /**
         * Kinematic viscosity [m²/s]
         */
        public static final double KINEMATIC_VISCOSITY = 1.004e-6; // m²/s at 20°C
        /**
         * Dynamic viscosity [kg/(m·s)]
         */
        public static final double DYNAMIC_VISCOSITY = 1.002e-3; // Pa·s at 20°C

        private Water() {
        }
    }

    /**
     * Environmental constants
     */
    public static final class Environment {

        /**
         * Gravitational acceleration [m/s²]
         */
        public static final double GRAVITY = 9.81; // m/s²
        /**
         * Atmospheric pressure [Pa]
         */
        public static final double ATMOSPHERIC_PRESSURE = 101325; // Pa

        private Environment() {
        }
    }

    private PhysicalConstants() {
    }
}

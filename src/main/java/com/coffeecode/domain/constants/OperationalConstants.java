package com.coffeecode.domain.constants;

/**
 * Operational parameters and limits
 */
public final class OperationalConstants {

    /**
     * Temperature ranges in Celsius
     */
    public static final class Temperature {

        public static final double MIN_OPERATING = 4.0;    // Minimum to prevent freezing
        public static final double MAX_OPERATING = 30.0;   // Maximum for water quality
        public static final double STANDARD = 20.0;        // Standard calculation temperature

        private Temperature() {
        }
    }

    /**
     * Pressure requirements in Pascals
     */
    public static final class Pressure {

        public static final double MIN_SERVICE = 137895.0;  // 20 PSI
        public static final double MAX_SERVICE = 551580.0;  // 80 PSI
        public static final double MIN_HYDRANT = 137895.0;  // Fire hydrant minimum

        private Pressure() {
        }
    }

    private OperationalConstants() {
    }
}

package com.coffeecode.domain.constants;

/**
 * Unit conversion factors
 */
public final class UnitConstants {

    /**
     * Pressure conversion factors
     */
    public static final class Pressure {

        public static final double PA_TO_PSI = 0.000145038;
        public static final double PSI_TO_PA = 6894.76;
        public static final double PA_TO_BAR = 0.00001;
        public static final double BAR_TO_PA = 100000.0;

        private Pressure() {
        }
    }

    /**
     * Flow rate conversion factors
     */
    public static final class Flow {

        public static final double M3S_TO_LPS = 1000.0;    // Cubic meters/s to L/s
        public static final double LPS_TO_M3S = 0.001;     // L/s to cubic meters/s

        private Flow() {
        }
    }

    private UnitConstants() {
    }
}

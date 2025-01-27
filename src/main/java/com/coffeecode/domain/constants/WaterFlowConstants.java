package com.coffeecode.domain.constants;

/**
 * Water flow constants for source-destination calculations All units are in SI
 * (m³/s for flow rates)
 */
public final class WaterFlowConstants {

    /**
     * Minimum allowable flow rate [m³/s] Represents minimum operational flow
     */
    public static final double MIN_FLOW_RATE = 0.001; // 1 L/s

    /**
     * Maximum allowable flow rate [m³/s] Based on typical distribution system
     * limits
     */
    public static final double MAX_FLOW_RATE = 2.0; // 2000 L/s

    /**
     * Peak hour factors for demand estimation
     */
    public static final class PeakFactors {

        /**
         * Residential area peak factor
         */
        public static final double RESIDENTIAL = 2.5;
        /**
         * Commercial area peak factor
         */
        public static final double COMMERCIAL = 1.8;
        /**
         * Industrial area peak factor
         */
        public static final double INDUSTRIAL = 1.5;

        private PeakFactors() {
        }
    }

    /**
     * Default flow rates [m³/s] based on pipe diameter
     */
    public static final class DefaultFlowRates {

        /**
         * Small pipe (D ≤ 200mm)
         */
        public static final double SMALL_PIPE = 0.05;
        /**
         * Medium pipe (200mm < D ≤ 500mm)
         */
        public static final double MEDIUM_PIPE = 0.2;
        /**
         * Large pipe (D > 500mm)
         */
        public static final double LARGE_PIPE = 0.5;

        private DefaultFlowRates() {
        }
    }

    private WaterFlowConstants() {
    }
}

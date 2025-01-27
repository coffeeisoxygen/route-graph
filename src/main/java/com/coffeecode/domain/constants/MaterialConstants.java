package com.coffeecode.domain.constants;

/**
 * Material properties constants for pipe calculations
 */
public final class MaterialConstants {

    /**
     * Pipe material roughness coefficients (k) in meters
     */
    public static final class Roughness {

        public static final double PVC = 0.0015e-3; // New PVC
        public static final double HDPE = 0.0015e-3; // High-density polyethylene
        public static final double STEEL_NEW = 0.045e-3; // New steel
        public static final double STEEL_USED = 0.15e-3; // Used steel
        public static final double CONCRETE = 0.3e-3; // Concrete
        public static final double IRON_CAST = 0.26e-3; // Cast iron
        public static final double MIN = 0.0001; // Minimum allowed
        public static final double MAX = 0.01; // Maximum allowed

        private Roughness() {
        }
    }

    public static final class PipeSizes {
        public static final double SMALL = 0.1; // [m]
        public static final double MEDIUM = 0.2; // [m]
        public static final double LARGE = 0.3; // [m]
        public static final double MIN = 0.05; // [m]
        public static final double MAX = 2.0; // [m]

        private PipeSizes() {
        }
    }

    private MaterialConstants() {
    }
}

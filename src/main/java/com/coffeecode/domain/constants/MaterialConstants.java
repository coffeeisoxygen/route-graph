package com.coffeecode.domain.constants;

/**
 * Material properties constants for pipe calculations
 */
public final class MaterialConstants {

    /**
     * Pipe material roughness coefficients (k) in meters
     */
    public static final class Roughness {

        public static final double PVC = 0.0015e-3;        // New PVC
        public static final double HDPE = 0.0015e-3;       // High-density polyethylene
        public static final double STEEL_NEW = 0.045e-3;   // New steel
        public static final double STEEL_USED = 0.15e-3;   // Used steel
        public static final double CONCRETE = 0.3e-3;      // Concrete
        public static final double IRON_CAST = 0.26e-3;    // Cast iron

        private Roughness() {
        }
    }

    private MaterialConstants() {
    }
}

package com.coffeecode.domain.values.pipe;

import com.coffeecode.domain.constants.MaterialConstants;
import com.coffeecode.domain.values.location.Distance;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.Value;

/**
 * Represents the physical properties of a pipe in a water distribution network.
 * Implements the Value Object pattern with immutable properties.
 */
@Value
public class PipeProperties {
    // Consider adding validation messages as constants
    private static final String MSG_NULL_MATERIAL = "Pipe material cannot be null";
    private static final String MSG_NULL_LENGTH = "Pipe length cannot be null";
    private static final String MSG_INVALID_DIAMETER = "Pipe diameter must be between %f and %f meters";

    // Use constants from MaterialConstants
    private static final double SMALL_DIAMETER = MaterialConstants.PipeSizes.SMALL;
    private static final double MEDIUM_DIAMETER = MaterialConstants.PipeSizes.MEDIUM;
    private static final double LARGE_DIAMETER = MaterialConstants.PipeSizes.LARGE;

    PipeMaterial material;
    double diameter;
    Distance length; // Immutable value object
    double roughness;

    private PipeProperties(Builder builder) {
        validateProperties(builder);
        this.material = builder.material;
        this.diameter = builder.diameter;
        // Defensive copy using factory method
        this.length = Distance.ofMeters(builder.length.getMetersValue());
        this.roughness = builder.material.getRoughness();
    }

    // Defensive copy on getter using factory method
    public Distance getLength() {
        return Distance.ofMeters(length.getMetersValue());
    }

    private void validateProperties(Builder builder) {
        if (builder.material == null) {
            throw ValidationException.nullOrEmpty("Pipe material");
        }
        if (builder.length == null) {
            throw ValidationException.nullOrEmpty("Pipe length");
        }
        validateDiameter(builder.diameter, builder.material);
    }

    private void validateDiameter(double diameter, PipeMaterial material) {
        if (diameter < material.getMinDiameter() ||
                diameter > material.getMaxDiameter()) {
            throw ValidationException.invalidRange("Pipe diameter",
                    material.getMinDiameter(),
                    material.getMaxDiameter());
        }
    }

    /**
     * Creates a standard PVC pipe configuration with small diameter
     * 
     * @param length pipe length in meters
     * @return PipeProperties instance with PVC material and small diameter
     */
    public static PipeProperties standardPVC(Distance length) {
        return builder()
                .material(PipeMaterial.PVC)
                .diameter(SMALL_DIAMETER) // Use constant instead of magic number
                .length(length)
                .build();
    }

    /**
     * Creates a standard HDPE pipe configuration with small diameter
     * 
     * @param length pipe length in meters
     * @return PipeProperties instance with HDPE material and small diameter
     */
    public static PipeProperties standardHDPE(Distance length) {
        return builder()
                .material(PipeMaterial.HDPE)
                .diameter(SMALL_DIAMETER)
                .length(length)
                .build();
    }

    /**
     * Creates a standard steel pipe configuration with medium diameter
     * 
     * @param length pipe length in meters
     * @return PipeProperties instance with steel material and medium diameter
     */
    public static PipeProperties standardSteel(Distance length) {
        return builder()
                .material(PipeMaterial.STEEL)
                .diameter(MEDIUM_DIAMETER)
                .length(length)
                .build();
    }

    /**
     * Creates a large PVC pipe configuration
     * 
     * @param length pipe length in meters
     * @return PipeProperties instance with PVC material and large diameter
     */
    public static PipeProperties largePVC(Distance length) {
        return builder()
                .material(PipeMaterial.PVC)
                .diameter(LARGE_DIAMETER)
                .length(length)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PipeMaterial material = PipeMaterial.PVC; // Default material
        private double diameter;
        private Distance length;

        public Builder material(PipeMaterial material) {
            this.material = material;
            return this;
        }

        public Builder diameter(double diameter) {
            this.diameter = diameter;
            return this;
        }

        public Builder length(Distance length) {
            this.length = length;
            return this;
        }

        public PipeProperties build() {
            return new PipeProperties(this);
        }
    }
}

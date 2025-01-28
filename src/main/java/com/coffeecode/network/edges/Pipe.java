package com.coffeecode.network.edges;

import lombok.Value;

/**
 * Represents a pipe segment in a hydraulic network.
 * All measurements in SI units (meters).
 */
@Value
public class Pipe {
    /** Minimum allowed pipe diameter in meters */
    private static final double MIN_DIAMETER = 0.01; // 10mm
    /** Maximum allowed pipe diameter in meters */
    private static final double MAX_DIAMETER = 3.0; // 3m
    /** Minimum allowed pipe length in meters */
    private static final double MIN_LENGTH = 0.1; // 10cm

    final double diameter;
    final double length;
    final PipeMaterial material;
    final double roughness;

    public Pipe(double diameter, double length, PipeMaterial material) {
        validateDiameter(diameter);
        validateLength(length);
        validateMaterial(material);

        this.diameter = diameter;
        this.length = length;
        this.material = material;
        this.roughness = material.getRoughness();
    }

    private void validateDiameter(double diameter) {
        if (diameter < MIN_DIAMETER || diameter > MAX_DIAMETER) {
            throw new IllegalArgumentException(
                    String.format("Pipe diameter must be between %.3f and %.1f meters",
                            MIN_DIAMETER, MAX_DIAMETER));
        }
    }

    private void validateLength(double length) {
        if (length < MIN_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("Pipe length must be at least %.1f meters", MIN_LENGTH));
        }
    }

    private void validateMaterial(PipeMaterial material) {
        if (material == null) {
            throw new IllegalArgumentException("Pipe material cannot be null");
        }
    }
}

package com.coffeecode.network.edges;

import lombok.Getter;

/**
 * Represents a pipe segment in a hydraulic network.
 * All measurements are in SI units (meters).
 */
@Getter
public class Pipe {
    private final double diameter; // diameter pipa (m)
    private final double length; // panjang pipa (m)
    private final PipeMaterial material; // material pipa
    private final double roughness; // roughness pipa (m) didapat dari material

    /**
     * Creates a new pipe segment.
     *
     * @param diameter the diameter of the pipe (m)
     * @param length   the length of the pipe (m)
     * @param material the material of the pipe
     */

    public Pipe(double diameter, double length, PipeMaterial material) {
        this.diameter = diameter;
        this.length = length;
        this.material = material;
        this.roughness = material.getRoughness(); // Mengambil roughness dari material
    }
}

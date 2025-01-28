package com.coffeecode.network.edges;

public enum PipeMaterial {
    PVC(0.0015e-3),
    STEEL(0.045e-3);

    private final double roughness;

    PipeMaterial(double roughness) {
        this.roughness = roughness;
    }

    public double getRoughness() {
        return roughness;
    }
}

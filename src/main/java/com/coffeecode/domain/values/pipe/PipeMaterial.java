package com.coffeecode.domain.values.pipe;

import com.coffeecode.domain.constants.MaterialConstants;

import lombok.Getter;

@Getter
public enum PipeMaterial {
    PVC(MaterialConstants.Roughness.PVC, 0.05, 0.5),
    HDPE(MaterialConstants.Roughness.HDPE, 0.05, 0.5),
    STEEL(MaterialConstants.Roughness.STEEL_NEW, 0.1, 2.0),
    CONCRETE(MaterialConstants.Roughness.CONCRETE, 0.3, 2.0);

    private final double roughness;
    private final double minDiameter;
    private final double maxDiameter;

    PipeMaterial(double roughness, double minDiameter, double maxDiameter) {
        this.roughness = roughness;
        this.minDiameter = minDiameter;
        this.maxDiameter = maxDiameter;
    }
}

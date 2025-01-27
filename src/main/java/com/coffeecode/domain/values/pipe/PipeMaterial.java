package com.coffeecode.domain.values.pipe;

import com.coffeecode.domain.constants.MaterialConstants;

import lombok.Getter;

@Getter
public enum PipeMaterial {
    PVC(MaterialConstants.Roughness.PVC,
            MaterialConstants.PipeSizes.MIN,
            MaterialConstants.PipeSizes.MEDIUM),
    HDPE(MaterialConstants.Roughness.HDPE,
            MaterialConstants.PipeSizes.MIN,
            MaterialConstants.PipeSizes.MEDIUM),
    STEEL(MaterialConstants.Roughness.STEEL_NEW,
            MaterialConstants.PipeSizes.MIN,
            MaterialConstants.PipeSizes.MAX),
    CONCRETE(MaterialConstants.Roughness.CONCRETE,
            MaterialConstants.PipeSizes.MEDIUM,
            MaterialConstants.PipeSizes.MAX);

    private final double roughness;
    private final double minDiameter;
    private final double maxDiameter;

    PipeMaterial(double roughness, double minDiameter, double maxDiameter) {
        this.roughness = roughness;
        this.minDiameter = minDiameter;
        this.maxDiameter = maxDiameter;
    }
}

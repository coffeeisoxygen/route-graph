package com.coffeecode.domain.values.fluid;

import com.coffeecode.domain.constants.PhysicalConstants;

public class WaterProperties implements FluidProperties {

    @Override
    public double getKinematicViscosity(double temperature) {
        return PhysicalConstants.Water.KINEMATIC_VISCOSITY;
    }

    @Override
    public double getDensity(double temperature) {
        return PhysicalConstants.Water.DENSITY;
    }

    @Override
    public double getDynamicViscosity(double temperature) {
        return PhysicalConstants.Water.DYNAMIC_VISCOSITY;
    }
}

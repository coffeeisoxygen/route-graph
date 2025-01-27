package com.coffeecode.domain.values.fluid;

public interface FluidProperties {

    double getKinematicViscosity(double temperature);

    double getDensity(double temperature);

    double getDynamicViscosity(double temperature);
}

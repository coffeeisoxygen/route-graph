package com.coffeecode.domain.values;

public interface FluidProperties {

    double getKinematicViscosity(double temperature);

    double getDensity(double temperature);

    double getDynamicViscosity(double temperature);
}

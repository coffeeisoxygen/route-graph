package com.coffeecode.network.calculator.physics.properties;

@FunctionalInterface
public interface FrictionFactorCalculator {
    double calculate(double reynolds, double roughness, double diameter);
}

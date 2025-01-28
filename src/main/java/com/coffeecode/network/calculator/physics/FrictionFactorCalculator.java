package com.coffeecode.network.calculator.physics;

@FunctionalInterface
public interface FrictionFactorCalculator {
    double calculate(double reynolds, double roughness, double diameter);
}

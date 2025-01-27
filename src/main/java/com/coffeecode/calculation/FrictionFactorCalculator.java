package com.coffeecode.calculation;

@FunctionalInterface
public interface FrictionFactorCalculator {
    double calculate(double reynolds, double roughness, double diameter);
}

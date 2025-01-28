package com.coffeecode.network.calculator.core;

import com.coffeecode.network.calculator.physics.FrictionFactorCalculator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DarcyWeisbachCalculator implements HeadLossCalculator {
    private static final double GRAVITY = 9.81;
    private static final double KINEMATIC_VISCOSITY = 1.004e-6;

    private final FrictionFactorCalculator frictionCalculator;

    public DarcyWeisbachCalculator(FrictionFactorCalculator frictionCalculator) {
        this.frictionCalculator = frictionCalculator;
    }

    @Override
    public double calculateTotalHead(double diameter, double length,
            double roughness, double velocity,
            double elevationDiff) {
        double reynolds = calculateReynolds(velocity, diameter);
        double friction = frictionCalculator.calculate(reynolds, roughness, diameter);

        double frictionLoss = friction * (length / diameter) *
                Math.pow(velocity, 2) / (2 * GRAVITY);

        return frictionLoss + Math.abs(elevationDiff);
    }

    private double calculateReynolds(double velocity, double diameter) {
        return (velocity * diameter) / KINEMATIC_VISCOSITY;
    }
}

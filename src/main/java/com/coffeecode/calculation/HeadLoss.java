package com.coffeecode.calculation;

import com.coffeecode.model.Pipe;

public class HeadLoss {
    private static final double GRAVITY = 9.81; // m/s²
    private static final double KINEMATIC_VISCOSITY = 1.004e-6; // m²/s

    public double calculateTotalHead(Pipe pipe) {
        double elevationLoss = pipe.getEnd().getElevation() -
                pipe.getStart().getElevation();
        double frictionLoss = calculateFrictionLoss(pipe);

        return elevationLoss + frictionLoss;
    }

    private double calculateFrictionLoss(Pipe pipe) {
        double velocity = 1.5; // Default velocity 1.5 m/s for now
        double reynolds = (velocity * pipe.getDiameter()) / KINEMATIC_VISCOSITY;
        double friction = calculateFrictionFactor(reynolds,
                pipe.getRoughness(),
                pipe.getDiameter());

        return friction * (pipe.getLength() / pipe.getDiameter()) *
                Math.pow(velocity, 2) / (2 * GRAVITY);
    }

    private double calculateFrictionFactor(double reynolds,
            double roughness,
            double diameter) {
        double relativeRoughness = roughness / diameter;
        return 0.25 / Math.pow(
                Math.log10(relativeRoughness / 3.7 + 5.74 / Math.pow(reynolds, 0.9)), 2);
    }
}

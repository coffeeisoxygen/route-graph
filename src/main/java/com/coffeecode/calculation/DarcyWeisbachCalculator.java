package com.coffeecode.calculation;

import com.coffeecode.config.AppProperties;
import com.coffeecode.model.Pipe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DarcyWeisbachCalculator implements HeadLossCalculator {
    private final AppProperties config;

    @Override
    public double calculateTotalHead(Pipe pipe) {
        validatePipe(pipe);
        double velocity = config.getDouble("water.velocity.default");
        validateVelocity(velocity);

        double frictionLoss = calculateFrictionLoss(pipe, velocity);
        double elevationLoss = Math.abs(pipe.getElevationDifference());

        return frictionLoss + elevationLoss;
    }

    private double calculateFrictionLoss(Pipe pipe, double velocity) {
        double viscosity = config.getDouble("water.kinematic.viscosity");
        double gravity = config.getDouble("environment.gravity");

        double reynolds = calculateReynolds(velocity, pipe.getDiameter(), viscosity);
        double friction = calculateFrictionFactor(reynolds, pipe.getRoughness(), pipe.getDiameter());

        return friction * (pipe.getLength() / pipe.getDiameter()) *
               Math.pow(velocity, 2) / (2 * gravity);
    }

    private double calculateReynolds(double velocity, double diameter, double viscosity) {
        double reynolds = (velocity * diameter) / viscosity;
        log.debug("Reynolds calculation: velocity={}, diameter={}, viscosity={}, result={}",
                velocity, diameter, viscosity, reynolds);
        return reynolds;
    }

    private double calculateFrictionFactor(double reynolds, double roughness, double diameter) {
        if (reynolds < 2300) {
            return 64 / reynolds;  // Laminar
        }
        // Turbulent - Colebrook-White
        double relativeRoughness = roughness / diameter;
        return 0.25 / Math.pow(
            Math.log10(relativeRoughness / 3.7 + 5.74 / Math.pow(reynolds, 0.9)), 2);
    }

    private void validatePipe(Pipe pipe) {
        if (pipe == null)
            throw new IllegalArgumentException("Pipe cannot be null");
        if (pipe.getDiameter() <= 0)
            throw new IllegalArgumentException("Invalid diameter");
    }

    private void validateVelocity(double velocity) {
        double min = config.getDouble("water.velocity.min");
        double max = config.getDouble("water.velocity.max");
        if (velocity < min || velocity > max) {
            throw new IllegalArgumentException(
                    String.format("Velocity must be between %.2f and %.2f m/s", min, max));
        }
        log.debug("Velocity validated: {} m/s", velocity);
    }
}

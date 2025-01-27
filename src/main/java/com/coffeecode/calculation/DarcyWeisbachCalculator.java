package com.coffeecode.calculation;

import java.util.EnumMap;
import java.util.Map;

import com.coffeecode.config.AppProperties;
import com.coffeecode.model.Pipe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DarcyWeisbachCalculator implements HeadLossCalculator {
    private static final String WATER_VELOCITY_KEY = "water.velocity.default";
    private static final String WATER_VISCOSITY_KEY = "water.kinematic.viscosity";
    private static final String GRAVITY_KEY = "environment.gravity";

    private final AppProperties config;
    private final Map<FlowRegime, FrictionFactorCalculator> frictionCalculators;

    public DarcyWeisbachCalculator(AppProperties config) {
        this.config = config;
        this.frictionCalculators = new EnumMap<>(FlowRegime.class);
        initializeFrictionCalculators();
    }

    private void initializeFrictionCalculators() {
        frictionCalculators.put(FlowRegime.LAMINAR,
                (reynolds, roughness, diameter) -> 64 / reynolds);
        frictionCalculators.put(FlowRegime.TRANSITION,
                this::calculateTransitionFriction);
        frictionCalculators.put(FlowRegime.TURBULENT,
                this::calculateTurbulentFriction);
    }

    @Override
    public double calculateTotalHead(Pipe pipe) {
        try {
            validatePipe(pipe);
            double velocity = config.getDouble(WATER_VELOCITY_KEY);
            validateVelocity(velocity);

            return calculateElevationLoss(pipe) + calculateFrictionLoss(pipe, velocity);
        } catch (Exception e) {
            log.error("Head loss calculation failed: {}", e.getMessage());
            throw new HeadLossCalculationException("Failed to calculate head loss", e);
        }
    }

    private double calculateFrictionLoss(Pipe pipe, double velocity) {
        double viscosity = config.getDouble(WATER_VISCOSITY_KEY);
        double gravity = config.getDouble(GRAVITY_KEY);

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

    private double calculateFrictionFactor(double reynolds,
            double roughness,
            double diameter) {
        FlowRegime regime = FlowRegime.fromReynolds(reynolds);
        log.debug("Flow regime: {} for Reynolds: {}", regime, reynolds);
        return frictionCalculators.get(regime)
                .calculate(reynolds, roughness, diameter);
    }

    private double calculateTurbulentFriction(double reynolds,
            double roughness,
            double diameter) {
        double relativeRoughness = roughness / diameter;
        return 0.25 / Math.pow(
                Math.log10(relativeRoughness / 3.7 + 5.74 / Math.pow(reynolds, 0.9)), 2);
    }

    private double calculateTransitionFriction(double reynolds,
            double roughness,
            double diameter) {
        double laminarFraction = (4000 - reynolds) / 1700;
        double laminarFriction = 64 / reynolds;
        double turbulentFriction = calculateTurbulentFriction(reynolds, roughness, diameter);
        return laminarFraction * laminarFriction + (1 - laminarFraction) * turbulentFriction;
    }

    private void validatePipe(Pipe pipe) {
        if (pipe == null)
            throw new IllegalArgumentException("Pipe cannot be null");
        if (pipe.getDiameter() <= 0)
            throw new IllegalArgumentException("Invalid diameter");
    }

    private double calculateElevationLoss(Pipe pipe) {
        return pipe.getElevationDifference();
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

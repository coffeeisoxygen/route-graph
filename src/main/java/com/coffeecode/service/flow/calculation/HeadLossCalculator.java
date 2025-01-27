package com.coffeecode.service.flow.calculation;

import org.springframework.stereotype.Component;

import com.coffeecode.domain.constants.PhysicalConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class HeadLossCalculator {

    public double calculate(double length, double diameter, double frictionFactor, double velocity) {
        HydraulicValidator.validateHydraulicParameters(length, diameter, velocity);
        HydraulicValidator.validateFrictionFactor(frictionFactor);

        double headLoss = frictionFactor * (length / diameter)
                * Math.pow(velocity, 2)
                / (2 * PhysicalConstants.Environment.GRAVITY);

        log.debug("Calculated head loss: {} m for length: {} m, diameter: {} m, friction: {}, velocity: {} m/s",
                headLoss, length, diameter, frictionFactor, velocity);

        return headLoss;
    }

    public double calculateFrictionFactor(double reynoldsNumber, double diameter, double roughness) {
        HydraulicValidator.validateReynoldsNumber(reynoldsNumber);
        HydraulicValidator.validateDiameter(diameter);
        HydraulicValidator.validateRoughness(roughness);

        double relativeRoughness = roughness / diameter;
        double friction = 0.25 / Math.pow(
                Math.log10(relativeRoughness / 3.7 + 5.74 / Math.pow(reynoldsNumber, 0.9)), 2);

        log.debug("Calculated friction factor: {} for Reynolds: {}, diameter: {} m, roughness: {} m",
                friction, reynoldsNumber, diameter, roughness);

        return friction;
    }
}

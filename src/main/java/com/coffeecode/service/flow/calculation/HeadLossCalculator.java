package com.coffeecode.service.flow.calculation;

import com.coffeecode.domain.constants.PhysicalConstants;
import com.coffeecode.validation.validators.HydraulicValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class HeadLossCalculator {

    public double calculate(double length, double diameter, double frictionFactor, double velocity) {
        HydraulicValidator.validateHydraulicParameters(length, diameter, velocity);

        log.debug("Calculating head loss with parameters: length={}, diameter={}, "
                + "friction={}, velocity={}", length, diameter, frictionFactor, velocity);

        double headLoss = frictionFactor * (length / diameter)
                * Math.pow(velocity, 2)
                / (2 * PhysicalConstants.Environment.GRAVITY);

        log.debug("Calculated head loss: {}", headLoss);
        return headLoss;
    }

    public double calculateFrictionFactor(double reynoldsNumber, double diameter, double roughness) {
        HydraulicValidator.validateReynoldsNumber(reynoldsNumber);

        double relativeRoughness = roughness / diameter;
        double friction = 0.25 / Math.pow(
                Math.log10(relativeRoughness / 3.7
                        + 5.74 / Math.pow(reynoldsNumber, 0.9)), 2);

        log.debug("Calculated friction factor: {}", friction);
        return friction;
    }
}

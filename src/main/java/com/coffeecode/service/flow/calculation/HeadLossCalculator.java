package com.coffeecode.service.flow.calculation;

import com.coffeecode.domain.constants.PhysicalConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class HeadLossCalculator {

    public double calculate(double length, double diameter, double frictionFactor, double velocity) {
        log.debug("Calculating head loss for length={}, diameter={}, friction={}, velocity={}",
                length, diameter, frictionFactor, velocity);
        return frictionFactor * (length / diameter) * Math.pow(velocity, 2) / (2 * PhysicalConstants.GRAVITY);
    }

    public double calculateFrictionFactor(double reynoldsNumber, double diameter, double roughness) {
        log.debug("Calculating friction factor for Re={}, diameter={}, roughness={}",
                reynoldsNumber, diameter, roughness);
        return 0.25 / Math.pow(
                Math.log10((roughness / diameter) / 3.7 + 5.74 / Math.pow(reynoldsNumber, 0.9)), 2);
    }
}

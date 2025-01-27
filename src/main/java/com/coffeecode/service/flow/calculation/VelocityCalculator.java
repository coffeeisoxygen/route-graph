package com.coffeecode.service.flow.calculation;

import org.springframework.stereotype.Component;

import com.coffeecode.domain.constants.PhysicalConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class VelocityCalculator {

    public double calculate(double pressure) {
        HydraulicValidator.validatePressure(pressure);
        double velocity = Math.sqrt((2 * pressure) / PhysicalConstants.Water.DENSITY);

        log.debug("Calculated velocity: {} m/s for pressure: {} Pa", velocity, pressure);
        return velocity;
    }

    public double calculateReynolds(double velocity, double diameter) {
        HydraulicValidator.validateVelocity(velocity);
        HydraulicValidator.validateDiameter(diameter);

        double reynolds = (velocity * diameter) / PhysicalConstants.Water.KINEMATIC_VISCOSITY;

        log.debug("Calculated Reynolds number: {} for velocity: {} m/s and diameter: {} m",
            reynolds, velocity, diameter);
        return reynolds;
    }
}

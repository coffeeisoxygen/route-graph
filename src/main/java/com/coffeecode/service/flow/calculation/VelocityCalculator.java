package com.coffeecode.service.flow.calculation;

import com.coffeecode.domain.constants.PhysicalConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class VelocityCalculator {

    public double calculate(double pressure) {
        return Math.sqrt((2 * pressure) / PhysicalConstants.WATER_DENSITY);
    }

    public double calculateWithReynolds(double velocity, double diameter) {
        return (velocity * diameter) / PhysicalConstants.KINEMATIC_VISCOSITY;
    }
}

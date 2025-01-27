package com.coffeecode.service.flow.calculation;

import org.springframework.stereotype.Component;

import com.coffeecode.domain.constants.PhysicalConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PressureCalculator {

    public double calculatePressureOut(double inputPressure, double headLoss) {
        HydraulicValidator.validatePressure(inputPressure);
        HydraulicValidator.validateHeadLoss(headLoss);

        double pressureLoss = headLoss * PhysicalConstants.Water.DENSITY
            * PhysicalConstants.Environment.GRAVITY;
        double pressureOut = inputPressure - pressureLoss;

        log.debug("Calculated pressure out: {} Pa from input: {} Pa and head loss: {} m",
            pressureOut, inputPressure, headLoss);

        return pressureOut;
    }
}

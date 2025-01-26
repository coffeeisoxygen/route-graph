package com.coffeecode.logic.flow.calculation;

import com.coffeecode.domain.constants.PhysicalConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PressureCalculator {

    public double calculatePressureOut(double inputPressure, double headLoss) {
        double pressureLoss = headLoss * PhysicalConstants.WATER_DENSITY * PhysicalConstants.GRAVITY;
        double pressureOut = inputPressure - pressureLoss;

        log.debug("Calculating pressure out: input={}, headLoss={}, result={}",
                inputPressure, headLoss, pressureOut);

        return Math.max(pressureOut, PhysicalConstants.ATMOSPHERIC_PRESSURE);
    }
}

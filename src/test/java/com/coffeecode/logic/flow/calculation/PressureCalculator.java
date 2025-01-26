package com.coffeecode.logic.flow.calculation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.coffeecode.domain.constants.PhysicalConstants;

/**
 * Unit tests for {@link PressureCalculator}.
 */
class PressureCalculatorTest {

    @InjectMocks
    private PressureCalculator pressureCalculator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test for {@link PressureCalculator#calculatePressureOut(double, double)}.
     */
    @Test
    void testCalculatePressureOut() {
        double inputPressure = 500000.0;
        double headLoss = 10.0;
        double expectedPressureOut = inputPressure - (headLoss * PhysicalConstants.WATER_DENSITY * PhysicalConstants.GRAVITY);
        expectedPressureOut = Math.max(expectedPressureOut, PhysicalConstants.ATMOSPHERIC_PRESSURE);

        double result = pressureCalculator.calculatePressureOut(inputPressure, headLoss);

        assertEquals(expectedPressureOut, result, 1e-6);
    }

    /**
     * Test for {@link PressureCalculator#calculatePressureOut(double, double)}
     * when the result is below atmospheric pressure.
     */
    @Test
    void testCalculatePressureOutBelowAtmospheric() {
        double inputPressure = 50000.0;
        double headLoss = 100.0;
        double expectedPressureOut = PhysicalConstants.ATMOSPHERIC_PRESSURE;

        double result = pressureCalculator.calculatePressureOut(inputPressure, headLoss);

        assertEquals(expectedPressureOut, result, 1e-6);
    }
}

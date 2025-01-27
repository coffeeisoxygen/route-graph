package com.coffeecode.logic.flow.calculation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.coffeecode.domain.constants.PhysicalConstants;
import com.coffeecode.service.flow.calculation.PressureCalculator;

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
        double inputPressure = 1000.0;
        double headLoss = 10.0;
        double expectedPressureOut = inputPressure - (headLoss * PhysicalConstants.WATER_DENSITY * PhysicalConstants.GRAVITY);

        double result = pressureCalculator.calculatePressureOut(inputPressure, headLoss);

        assertEquals(expectedPressureOut, result, 1e-6);
    }
}

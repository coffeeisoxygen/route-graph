package com.coffeecode.logic.flow.calculation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.coffeecode.domain.constants.PhysicalConstants;
import com.coffeecode.service.flow.calculation.HeadLossCalculator;

/**
 * Unit tests for {@link HeadLossCalculator}.
 */
class HeadLossCalculatorTest {

    @InjectMocks
    private HeadLossCalculator headLossCalculator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test for
     * {@link HeadLossCalculator#calculate(double, double, double, double)}.
     */
    @Test
    void testCalculateHeadLoss() {
        double length = 100.0;
        double diameter = 0.5;
        double frictionFactor = 0.02;
        double velocity = 2.0;
        double expectedHeadLoss = frictionFactor * (length / diameter) * Math.pow(velocity, 2) / (2 * PhysicalConstants.GRAVITY);

        double result = headLossCalculator.calculate(length, diameter, frictionFactor, velocity);

        assertEquals(expectedHeadLoss, result, 1e-6);
    }

    /**
     * Test for
     * {@link HeadLossCalculator#calculateFrictionFactor(double, double, double)}.
     */
    @Test
    void testCalculateFrictionFactor() {
        double reynoldsNumber = 10000.0;
        double diameter = 0.5;
        double roughness = 0.0001;
        double expectedFrictionFactor = 0.25 / Math.pow(
                Math.log10((roughness / diameter) / 3.7 + 5.74 / Math.pow(reynoldsNumber, 0.9)), 2);

        double result = headLossCalculator.calculateFrictionFactor(reynoldsNumber, diameter, roughness);

        assertEquals(expectedFrictionFactor, result, 1e-6);
    }
}

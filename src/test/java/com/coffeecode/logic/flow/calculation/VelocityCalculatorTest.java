package com.coffeecode.logic.flow.calculation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.coffeecode.domain.constants.PhysicalConstants;

/**
 * Unit tests for {@link VelocityCalculator}.
 */
class VelocityCalculatorTest {

    @InjectMocks
    private VelocityCalculator velocityCalculator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test for {@link VelocityCalculator#calculate(double)}.
     */
    @Test
    void testCalculateVelocity() {
        double pressure = 1000.0;
        double expectedVelocity = Math.sqrt((2 * pressure) / PhysicalConstants.WATER_DENSITY);

        double result = velocityCalculator.calculate(pressure);

        assertEquals(expectedVelocity, result, 1e-6);
    }

    /**
     * Test for {@link VelocityCalculator#calculateWithReynolds(double, double)}.
     */
    @Test
    void testCalculateWithReynolds() {
        double velocity = 2.0;
        double diameter = 0.5;
        double expectedReynoldsNumber = (velocity * diameter) / PhysicalConstants.KINEMATIC_VISCOSITY;

        double result = velocityCalculator.calculateWithReynolds(velocity, diameter);

        assertEquals(expectedReynoldsNumber, result, 1e-6);
    }
}

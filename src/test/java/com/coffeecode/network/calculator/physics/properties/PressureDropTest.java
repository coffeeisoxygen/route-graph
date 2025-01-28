package com.coffeecode.network.calculator.physics.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Pressure Drop Tests")
class PressureDropTest {
    private static final double DELTA = 1.0; // Pascal tolerance
    private static final double WATER_DENSITY = 998.2;
    private static final double GRAVITY = 9.81;

    @Test
    @DisplayName("Should calculate pressure drop from head loss only")
    void shouldCalculatePressureDropFromHeadLoss() {
        // Given
        HeadLoss headLoss = new HeadLoss(10.0); // 10m head loss
        double elevationDiff = 0.0; // No elevation change
        double expected = WATER_DENSITY * GRAVITY * 10.0;

        // When
        PressureDrop pressureDrop = PressureDrop.calculate(headLoss, elevationDiff);

        // Then
        assertEquals(expected, pressureDrop.getValue(), DELTA);
    }

    @Test
    @DisplayName("Should include elevation effects")
    void shouldIncludeElevationEffects() {
        // Given
        HeadLoss headLoss = new HeadLoss(5.0); // 5m head loss
        double elevationDiff = 10.0; // 10m elevation rise
        double expected = WATER_DENSITY * GRAVITY * 15.0;

        // When
        PressureDrop pressureDrop = PressureDrop.calculate(headLoss, elevationDiff);

        // Then
        assertEquals(expected, pressureDrop.getValue(), DELTA);
    }

    @Test
    @DisplayName("Should handle negative elevation difference")
    void shouldHandleNegativeElevation() {
        // Given
        HeadLoss headLoss = new HeadLoss(8.0); // 8m head loss
        double elevationDiff = -5.0; // 5m elevation drop
        double expected = WATER_DENSITY * GRAVITY * 3.0;

        // When
        PressureDrop pressureDrop = PressureDrop.calculate(headLoss, elevationDiff);

        // Then
        assertEquals(expected, pressureDrop.getValue(), DELTA);
    }
}

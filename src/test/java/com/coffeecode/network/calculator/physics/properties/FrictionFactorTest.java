package com.coffeecode.network.calculator.physics.properties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Friction Factor Tests")
class FrictionFactorTest {
    private static final double DELTA = 1e-6;
    private static final double PIPE_DIAMETER = 0.1; // 100mm
    private static final double PIPE_ROUGHNESS = 0.0015e-3; // PVC

    @Test
    @DisplayName("Should calculate laminar friction factor")
    void shouldCalculateLaminarFriction() {
        // Given
        ReynoldsNumber reynolds = new ReynoldsNumber(2000); // Laminar flow
        double expected = 64.0 / 2000; // f = 64/Re for laminar

        // When
        FrictionFactor f = FrictionFactor.calculate(reynolds, PIPE_ROUGHNESS, PIPE_DIAMETER);

        // Then
        assertEquals(expected, f.getValue(), DELTA);
    }

    @Test
    @DisplayName("Should calculate turbulent friction factor")
    void shouldCalculateTurbulentFriction() {
        // Given
        ReynoldsNumber reynolds = new ReynoldsNumber(10000); // Turbulent flow
        double expected = 0.031; // Verified with external calculator

        // When
        FrictionFactor f = FrictionFactor.calculate(reynolds, PIPE_ROUGHNESS, PIPE_DIAMETER);

        // Then
        assertEquals(expected, f.getValue(), 0.001);
    }

    @ParameterizedTest
    @CsvSource({
            "2000, 0.032", // Laminar boundary
            "3000, 0.04353261103755045", // Transitional
            "4000, 0.03992219531458709" // Turbulent boundary
    })
    @DisplayName("Should handle flow regime transitions")
    void shouldHandleFlowRegimeTransitions(double re, double expected) {
        // Given
        ReynoldsNumber reynolds = new ReynoldsNumber(re);

        // When
        FrictionFactor f = FrictionFactor.calculate(reynolds, PIPE_ROUGHNESS, PIPE_DIAMETER);

        // Then
        assertEquals(expected, f.getValue(), 0.001);
    }

    @Test
    @DisplayName("Should converge for realistic pipe conditions")
    void shouldConvergeForRealisticConditions() {
        // Given
        ReynoldsNumber reynolds = new ReynoldsNumber(50000);

        // When/Then
        assertDoesNotThrow(() -> FrictionFactor.calculate(reynolds, PIPE_ROUGHNESS, PIPE_DIAMETER));
    }
}

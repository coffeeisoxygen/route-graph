package com.coffeecode.network.calculator.physics.properties;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("FlowVelocity Tests")
class FlowVelocityTest {
    private static final double DELTA = 0.001;

    @Test
    @DisplayName("Should create valid flow velocity")
    void shouldCreateValidFlowVelocity() {
        // Given
        double velocity = 1.0;

        // When
        FlowVelocity flow = FlowVelocity.of(velocity);

        // Then
        assertEquals(velocity, flow.getValue());
    }

    @Test
    @DisplayName("Should calculate velocity from flow rate")
    void shouldCalculateFromFlowRate() {
        // Given
        double flowRate = 0.047; // m³/s (adjusted for valid velocity)
        double diameter = 0.2; // m
        double expectedVelocity = 1.4960564650638162; // m/s (within valid range)

        // When
        FlowVelocity flow = FlowVelocity.fromFlowRate(flowRate, diameter);

        // Then
        assertEquals(expectedVelocity, flow.getValue(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = { 0.0, -1.0, 4.0 })
    @DisplayName("Should reject invalid velocities")
    void shouldRejectInvalidVelocities(double invalidVelocity) {
        assertThrows(IllegalArgumentException.class,
                () -> FlowVelocity.of(invalidVelocity));
    }

    @ParameterizedTest
    @ValueSource(doubles = { 0.3, 1.5, 3.0 })
    @DisplayName("Should accept boundary velocities")
    void shouldAcceptBoundaryVelocities(double velocity) {
        assertDoesNotThrow(() -> FlowVelocity.of(velocity));
    }

    @ParameterizedTest
    @ValueSource(doubles = { 0.2, 3.1 })
    @DisplayName("Should reject out of range velocities")
    void shouldRejectOutOfRangeVelocities(double velocity) {
        assertThrows(IllegalArgumentException.class,
                () -> FlowVelocity.of(velocity));
    }

    @Test
    @DisplayName("Should reject non-finite values")
    void shouldRejectNonFiniteValues() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> FlowVelocity.of(Double.POSITIVE_INFINITY)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> FlowVelocity.of(Double.NaN)));
    }
}

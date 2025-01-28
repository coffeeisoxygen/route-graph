package com.coffeecode.network.calculator.physics.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Head Loss Tests")
class HeadLossTest {
    private static final double DELTA = 1e-6;
    private static final double PIPE_LENGTH = 100.0; // m
    private static final double PIPE_DIAMETER = 0.1; // m
    private static final double FLOW_VELOCITY = 1.0; // m/s
    private static final double FRICTION_FACTOR = 0.02; // typical value

    @Test
    @DisplayName("Should calculate head loss correctly")
    void shouldCalculateHeadLoss() {
        // Given
        FrictionFactor friction = new FrictionFactor(FRICTION_FACTOR);
        FlowVelocity velocity = FlowVelocity.of(FLOW_VELOCITY);

        // Expected: hf = f * (L/D) * (v²/2g)
        double expected = FRICTION_FACTOR * (PIPE_LENGTH / PIPE_DIAMETER) *
                (FLOW_VELOCITY * FLOW_VELOCITY) / (2 * 9.81);

        // When
        HeadLoss headLoss = HeadLoss.calculate(friction, PIPE_LENGTH,
                PIPE_DIAMETER, velocity);

        // Then
        assertEquals(expected, headLoss.getValue(), DELTA);
    }

    @Test
    @DisplayName("Should reject negative length")
    void shouldRejectNegativeLength() {
        // Given
        FrictionFactor friction = new FrictionFactor(FRICTION_FACTOR);
        FlowVelocity velocity = FlowVelocity.of(FLOW_VELOCITY);

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> HeadLoss.calculate(friction, -1.0, PIPE_DIAMETER, velocity));
    }

    @Test
    @DisplayName("Should reject zero diameter")
    void shouldRejectZeroDiameter() {
        // Given
        FrictionFactor friction = new FrictionFactor(FRICTION_FACTOR);
        FlowVelocity velocity = FlowVelocity.of(FLOW_VELOCITY);

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> HeadLoss.calculate(friction, PIPE_LENGTH, 0.0, velocity));
    }

    @Test
    @DisplayName("Should handle typical pipe conditions")
    void shouldHandleTypicalConditions() {
        // Given: PVC pipe, turbulent flow
        FrictionFactor friction = new FrictionFactor(0.02);
        FlowVelocity velocity = FlowVelocity.of(1.5);
        double length = 1000.0;
        double diameter = 0.2;

        // When
        HeadLoss headLoss = HeadLoss.calculate(friction, length,
                diameter, velocity);

        // Then
        assertTrue(headLoss.getValue() > 0);
        assertTrue(headLoss.getValue() < length);
    }
}

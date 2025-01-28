package com.coffeecode.network.calculator.flow;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.network.calculator.physics.properties.FlowRate;
import com.coffeecode.network.calculator.physics.properties.HeadLoss;

@DisplayName("Network Flow Tests")
class NetworkFlowTest {
    private static final double INITIAL_PRESSURE = 300000.0; // 300 kPa
    private static final double FLOW_RATE = 0.05; // 50 L/s
    private static final double HEAD_LOSS = 10.0; // 10m
    private static final double ELEVATION_DIFF = 5.0; // 5m up

    @Test
    @DisplayName("Should calculate valid network flow")
    void shouldCalculateValidFlow() {
        // Given
        FlowRate flowRate = FlowRate.of(FLOW_RATE);
        HeadLoss headLoss = new HeadLoss(HEAD_LOSS);

        // When
        NetworkFlow flow = NetworkFlow.calculate(
                INITIAL_PRESSURE, flowRate, headLoss, ELEVATION_DIFF);

        // Then
        assertAll(
                () -> assertEquals(flowRate, flow.getFlowRate()),
                () -> assertTrue(flow.isValid()),
                () -> assertNotNull(flow.getPressureDrop()));
    }

    @Test
    @DisplayName("Should detect invalid flow due to insufficient pressure")
    void shouldDetectInvalidFlow() {
        // Given
        double lowPressure = 1000.0; // 1 kPa (too low)
        FlowRate flowRate = FlowRate.of(FLOW_RATE);
        HeadLoss headLoss = new HeadLoss(HEAD_LOSS);

        // When
        NetworkFlow flow = NetworkFlow.calculate(
                lowPressure, flowRate, headLoss, ELEVATION_DIFF);

        // Then
        assertFalse(flow.isValid());
    }

    @Test
    @DisplayName("Should handle zero elevation difference")
    void shouldHandleZeroElevation() {
        // Given
        FlowRate flowRate = FlowRate.of(FLOW_RATE);
        HeadLoss headLoss = new HeadLoss(HEAD_LOSS);
        double noElevation = 0.0;

        // When
        NetworkFlow flow = NetworkFlow.calculate(
                INITIAL_PRESSURE, flowRate, headLoss, noElevation);

        // Then
        assertTrue(flow.isValid());
    }
}

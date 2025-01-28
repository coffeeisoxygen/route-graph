package com.coffeecode.network.calculator.physics.properties;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Reynolds Number Tests")
class ReynoldsNumberTest {
    private static final double DELTA = 0.001;

    @Test
    @DisplayName("Should calculate Reynolds number correctly")
    void shouldCalculateReynoldsNumber() {
        // Given
        FlowVelocity velocity = FlowVelocity.of(1.0);
        double diameter = 0.1;
        double expected = 99601.593625498; // (1.0 * 0.1) / 1.004e-6

        // When
        ReynoldsNumber reynolds = ReynoldsNumber.calculate(velocity, diameter);

        // Then
        assertEquals(expected, reynolds.getValue(), DELTA);
    }

    @ParameterizedTest
    @CsvSource({
            "2000.0, LAMINAR",
            "3000.0, TRANSITIONAL",
            "5000.0, TURBULENT"
    })
    @DisplayName("Should classify flow regime correctly")
    void shouldClassifyFlowRegime(double value, FlowRegime expectedRegime) {
        // Given
        ReynoldsNumber reynolds = new ReynoldsNumber(value);

        // When
        FlowRegime regime = reynolds.getFlowRegime();

        // Then
        assertEquals(expectedRegime, regime);
    }

    @Test
    @DisplayName("Should handle boundary conditions")
    void shouldHandleBoundaryConditions() {
        assertAll(
                "Flow regime boundaries",
                () -> assertEquals(FlowRegime.LAMINAR,
                        new ReynoldsNumber(ReynoldsNumber.LAMINAR_THRESHOLD - 1).getFlowRegime()),
                () -> assertEquals(FlowRegime.TRANSITIONAL,
                        new ReynoldsNumber(ReynoldsNumber.LAMINAR_THRESHOLD + 1).getFlowRegime()),
                () -> assertEquals(FlowRegime.TRANSITIONAL,
                        new ReynoldsNumber(ReynoldsNumber.TURBULENT_THRESHOLD - 1).getFlowRegime()),
                () -> assertEquals(FlowRegime.TURBULENT,
                        new ReynoldsNumber(ReynoldsNumber.TURBULENT_THRESHOLD + 1).getFlowRegime()));
    }
}

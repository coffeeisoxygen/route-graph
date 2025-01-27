package com.coffeecode.calculation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.coffeecode.config.AppProperties;
import com.coffeecode.model.Node;
import com.coffeecode.model.Pipe;

@DisplayName("Head Loss Calculations")
class HeadLossTest {

    private HeadLoss calculator;
    private Pipe pipe;
    private AppProperties config;

    @BeforeEach
    void setUp() {
        calculator = new HeadLoss();
        config = AppProperties.getInstance();

        // Create test pipe with 5m elevation drop
        Node start = new Node(0.0, 0.0, 100.0, config.getDouble("water.velocity.default"));
        Node end = new Node(100.0, 0.0, 95.0, config.getDouble("water.velocity.default"));
        pipe = new Pipe(start, end, 100.0, 0.1, config.getDouble("pipe.roughness.pvc"));
    }

    @Test
    @DisplayName("Should calculate total head loss correctly")
    void shouldCalculateTotalHeadLossCorrectly() {
        // When
        double totalHead = calculator.calculateTotalHead(pipe);

        // Then
        double expectedElevationLoss = -5.0;
        assertTrue(totalHead > expectedElevationLoss,
                "Total head loss should be greater than elevation loss due to friction");
        assertTrue(totalHead < 0.0,
                "Total head loss should be negative for downward flow");
    }

    @Test
    @DisplayName("Should handle zero length pipe")
    void shouldHandleZeroLengthPipe() {
        // Given
        Node start = new Node(0.0, 0.0, 100.0, config.getDouble("water.velocity.default"));
        Node end = new Node(0.0, 0.0, 100.0, config.getDouble("water.velocity.default"));
        Pipe zeroPipe = new Pipe(start, end, 0.0, 0.1, config.getDouble("pipe.roughness.pvc"));

        // When
        double totalHead = calculator.calculateTotalHead(zeroPipe);

        // Then
        assertEquals(0.0, totalHead, "Head loss should be zero for zero length pipe");
    }

    @ParameterizedTest
    @CsvSource({
            "0.05, 0.0015E-3", // Minimum diameter
            "2.0, 0.045E-3" // Maximum diameter
    })
    @DisplayName("Should handle boundary conditions")
    void shouldHandleBoundaryConditions(double diameter, double roughness) {
        // Given
        Pipe testPipe = new Pipe(pipe.getStart(), pipe.getEnd(),
                pipe.getLength(), diameter, roughness);

        // When
        double totalHead = calculator.calculateTotalHead(testPipe);

        // Then
        assertTrue(totalHead < 0.0,
                "Head loss should be negative for downward flow");
    }
}

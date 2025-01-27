package com.coffeecode.calculation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.model.Node;
import com.coffeecode.model.Pipe;

class HeadLossTest {

    private HeadLoss calculator;
    private Pipe pipe;

    @BeforeEach
    void setUp() {
        calculator = new HeadLoss();
        Node start = new Node(0.0, 0.0, 100.0, 101325.0);
        Node end = new Node(100.0, 0.0, 95.0, 101325.0);
        pipe = new Pipe(start, end, 100.0, 0.1, Pipe.PVC_ROUGHNESS);
    }

    @Test
    @DisplayName("Should calculate total head loss correctly")
    void shouldCalculateTotalHeadLossCorrectly() {
        // When
        double totalHead = calculator.calculateTotalHead(pipe);

        // Then
        double expectedElevationLoss = -5.0; // end.elevation - start.elevation
        assertTrue(totalHead > expectedElevationLoss); // Should include friction losses
    }

    @Test
    @DisplayName("Should handle zero length pipe")
    void shouldHandleZeroLengthPipe() {
        // Given
        Node start = new Node(0.0, 0.0, 100.0, 101325.0);
        Node end = new Node(0.0, 0.0, 100.0, 101325.0);
        Pipe zeroPipe = new Pipe(start, end, 0.0, 0.1, Pipe.PVC_ROUGHNESS);

        // When
        double totalHead = calculator.calculateTotalHead(zeroPipe);

        // Then
        assertEquals(0.0, totalHead);
    }
}

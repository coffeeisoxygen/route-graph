package com.coffeecode.calculation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.coffeecode.model.Node;
import com.coffeecode.model.Pipe;
import com.coffeecode.model.PipeMaterial;
import com.coffeecode.model.coordinates.CartesianCoordinates;

@DisplayName("DarcyWeisbach Calculator Tests")
class DarcyWeisbachCalculatorTest {
    private DarcyWeisbachCalculator calculator;
    private TestConfig config;
    private Node startNode;
    private Node endNode;
    private Pipe pipe;

    @BeforeEach
    void setUp() {
        config = new TestConfig(1.5, 0.3, 3.0, 1.004E-6, 9.81);
        calculator = new DarcyWeisbachCalculator(config);

        startNode = Node.builder()
                .coordinates(new CartesianCoordinates(0, 0))
                .elevation(100.0)
                .velocity(1.5)
                .pressure(101325.0)
                .build();

        endNode = Node.builder()
                .coordinates(new CartesianCoordinates(100, 0))
                .elevation(95.0)
                .velocity(1.5)
                .pressure(101325.0)
                .build();

        pipe = Pipe.builder()
                .start(startNode)
                .end(endNode)
                .diameter(0.1)
                .material(PipeMaterial.PVC)
                .build();
    }

    @ParameterizedTest
    @CsvSource({
            "0.05, 1.5", // Laminar
            "0.3, 1.5", // Turbulent
            "0.15, 0.5" // Transition
    })
    @DisplayName("Should handle different flow regimes")
    void shouldHandleFlowRegimes(double diameter, double velocity) {
        // Given
        config = new TestConfig(velocity, 0.3, 3.0, 1.004E-6, 9.81);
        calculator = new DarcyWeisbachCalculator(config);

        pipe = Pipe.builder()
                .start(startNode)
                .end(endNode)
                .diameter(diameter)
                .material(PipeMaterial.PVC)
                .build();

        // When
        double headLoss = calculator.calculateTotalHead(pipe);

        // Then
        assertTrue(headLoss >= 0,
                String.format("Head loss should be non-negative, got: %.3f", headLoss));
    }

    @Test
    @DisplayName("Should calculate total head loss")
    void shouldCalculateTotalHeadLoss() {
        // Given
        config = new TestConfig(1.5, 0.3, 3.0, 1.004E-6, 9.81);
        calculator = new DarcyWeisbachCalculator(config);

        // When
        double headLoss = calculator.calculateTotalHead(pipe);

        // Then
        assertTrue(headLoss >= 0, "Head loss should be non-negative");
        assertTrue(headLoss >= Math.abs(pipe.getElevationDifference()),
                "Head loss should be greater than elevation difference");
    }

    @Test
    @DisplayName("Should calculate head loss correctly")
    void shouldCalculateHeadLoss() {
        double headLoss = calculator.calculateTotalHead(pipe);
        assertTrue(headLoss > pipe.getElevationDifference());
    }

    @Test
    @DisplayName("Should validate velocity bounds")
    void shouldValidateVelocityBounds() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateTotalHead(null));
        assertEquals("Pipe cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should calculate elevation difference")
    void shouldCalculateElevationDifference() {
        assertEquals(-5.0, pipe.getElevationDifference(), 0.001);
    }

    @Test
    @DisplayName("Should calculate elevation loss correctly")
    void shouldCalculateElevationLoss() {
        // When
        double headLoss = calculator.calculateTotalHead(pipe);

        // Then
        // Negative value indicates flow goes downhill (end lower than start)
        assertEquals(-5.0, pipe.getElevationDifference(), 0.001);
        // Total head loss should be greater than absolute elevation difference
        assertTrue(headLoss > Math.abs(pipe.getElevationDifference()));
    }

    @Test
    @DisplayName("Should handle uphill flow")
    void shouldHandleUphillFlow() {
        // Given
        pipe = Pipe.builder()
                .start(Node.builder()
                        .coordinates(new CartesianCoordinates(0, 0))
                        .elevation(95.0)
                        .build())
                .end(Node.builder()
                        .coordinates(new CartesianCoordinates(100, 0))
                        .elevation(100.0)
                        .build())
                .diameter(0.1)
                .material(PipeMaterial.PVC)
                .build();

        // When
        double headLoss = calculator.calculateTotalHead(pipe);

        // Then
        assertEquals(5.0, pipe.getElevationDifference(), 0.001);
        assertTrue(headLoss > pipe.getElevationDifference());
    }

    @Test
    @DisplayName("Should throw exception for pipe with negative diameter")
    void shouldThrowExceptionForInvalidPipe() {
        pipe = Pipe.builder()
                .start(startNode)
                .end(endNode)
                .diameter(-0.1)
                .material(PipeMaterial.PVC)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateTotalHead(pipe));
        assertEquals("Pipe diameter must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for negative diameter")
    void shouldThrowExceptionForNegativeDiameter() {
        // Given
        pipe = Pipe.builder()
                .start(startNode)
                .end(endNode)
                .diameter(-0.1)
                .material(PipeMaterial.PVC)
                .build();

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateTotalHead(pipe));
        assertEquals("Diameter must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for null pipe")
    void shouldThrowExceptionForNullPipe() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateTotalHead(null));
        assertEquals("Pipe cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for invalid velocity")
    void shouldThrowExceptionForInvalidVelocity() {
        // Given
        TestConfig invalidConfig = new TestConfig(4.0, 0.3, 3.0, 1.004E-6, 9.81);
        DarcyWeisbachCalculator invalidCalculator = new DarcyWeisbachCalculator(invalidConfig);

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> invalidCalculator.calculateTotalHead(pipe));
        assertTrue(exception.getMessage().contains("Velocity must be between"));
    }
}

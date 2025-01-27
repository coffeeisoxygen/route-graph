package com.coffeecode.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.model.coordinates.CartesianCoordinates;

@DisplayName("Pipe Tests")
class PipeTest {
    private Node startNode;
    private Node endNode;

    @BeforeEach
    void setUp() {
        startNode = Node.builder()
                .coordinates(new CartesianCoordinates(0, 0))
                .elevation(100.0)
                .velocity(1.5)
                .pressure(101325)
                .build();

        endNode = Node.builder()
                .coordinates(new CartesianCoordinates(100, 0))
                .elevation(95.0)
                .velocity(1.5)
                .pressure(101325)
                .build();
    }

    @Test
    @DisplayName("Should create pipe with valid parameters")
    void shouldCreatePipeWithValidParameters() {
        // When
        Pipe pipe = Pipe.builder()
                .start(startNode)
                .end(endNode)
                .diameter(0.1)
                .material(PipeMaterial.PVC)
                .build();

        // Then
        assertEquals(startNode, pipe.getStart());
        assertEquals(endNode, pipe.getEnd());
        assertEquals(0.1, pipe.getDiameter());
        assertEquals(PipeMaterial.PVC, pipe.getMaterial());
        assertEquals(100.0, pipe.getLength(), 0.001); // 100m length
    }

    @Test
    @DisplayName("Should calculate elevation difference")
    void shouldCalculateElevationDifference() {
        // When
        Pipe pipe = Pipe.builder()
                .start(startNode)
                .end(endNode)
                .diameter(0.1)
                .material(PipeMaterial.PVC)
                .build();

        // Then
        assertEquals(-5.0, pipe.getElevationDifference(), 0.001);
    }

    @Test
    @DisplayName("Should throw exception for null start node")
    void shouldThrowExceptionForNullStartNode() {
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> Pipe.builder()
                .start(null)
                .end(endNode)
                .diameter(0.1)
                .material(PipeMaterial.PVC)
                .build());
    }

    @Test
    @DisplayName("Should throw exception for invalid diameter")
    void shouldThrowExceptionForInvalidDiameter() {
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> Pipe.builder()
                .start(startNode)
                .end(endNode)
                .diameter(-0.1)
                .material(PipeMaterial.PVC)
                .build());
    }

    @Test
    @DisplayName("Should get correct roughness from material")
    void shouldGetCorrectRoughness() {
        // When
        Pipe pipe = Pipe.builder()
                .start(startNode)
                .end(endNode)
                .diameter(0.1)
                .material(PipeMaterial.PVC)
                .build();

        // Then
        assertEquals(0.0015e-3, pipe.getRoughness(), 0.0000001);
    }

    @Test
    @DisplayName("Should create pipe with specified length")
    void shouldCreatePipeWithSpecifiedLength() {
        // When
        Pipe pipe = Pipe.builder()
                .start(startNode)
                .end(endNode)
                .length(150.0)
                .diameter(0.1)
                .material(PipeMaterial.PVC)
                .build();

        // Then
        assertEquals(150.0, pipe.getLength(), 0.001);
    }

    @Test
    @DisplayName("Should calculate length if not specified")
    void shouldCalculateLengthIfNotSpecified() {
        // When
        Pipe pipe = Pipe.builder()
                .start(startNode)
                .end(endNode)
                .diameter(0.1)
                .material(PipeMaterial.PVC)
                .build();

        // Then
        assertEquals(100.0, pipe.getLength(), 0.001); // 100m length calculated from coordinates
    }

    @Test
    @DisplayName("Should throw exception for negative length")
    void shouldThrowExceptionForNegativeLength() {
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> Pipe.builder()
                .start(startNode)
                .end(endNode)
                .length(-50.0)
                .diameter(0.1)
                .material(PipeMaterial.PVC)
                .build());
    }
}

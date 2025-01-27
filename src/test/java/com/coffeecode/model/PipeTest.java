package com.coffeecode.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PipeTest {

    private Node startNode;
    private Node endNode;

    @BeforeEach
    void setUp() {
        startNode = new Node(0.0, 0.0, 100.0, 101325.0);
        endNode = new Node(100.0, 0.0, 95.0, 101325.0);
    }

    @Test
    @DisplayName("Should create pipe with valid parameters")
    void shouldCreatePipeWithValidParameters() {
        // Given
        double length = 100.0;
        double diameter = 0.1;
        double roughness = Pipe.PVC_ROUGHNESS;

        // When
        Pipe pipe = new Pipe(startNode, endNode, length, diameter, roughness);

        // Then
        assertEquals(startNode, pipe.getStart());
        assertEquals(endNode, pipe.getEnd());
        assertEquals(length, pipe.getLength());
        assertEquals(diameter, pipe.getDiameter());
        assertEquals(roughness, pipe.getRoughness());
    }

    @Test
    @DisplayName("Should have correct PVC roughness constant")
    void shouldHaveCorrectPVCRoughness() {
        assertEquals(0.0015e-3, Pipe.PVC_ROUGHNESS);
    }
}

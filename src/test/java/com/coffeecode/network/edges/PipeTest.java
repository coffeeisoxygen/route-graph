package com.coffeecode.network.edges;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayName("Pipe Tests")
class PipeTest {

    @Test
    @DisplayName("Should create pipe with valid parameters")
    void shouldCreateValidPipe() {
        // Given
        double diameter = 0.1; // 100mm
        double length = 10.0; // 10m
        PipeMaterial material = PipeMaterial.PVC;

        // When
        Pipe pipe = new Pipe(diameter, length, material);

        // Then
        assertEquals(diameter, pipe.getDiameter());
        assertEquals(length, pipe.getLength());
        assertEquals(material, pipe.getMaterial());
        assertEquals(material.getRoughness(), pipe.getRoughness());
    }

    @ParameterizedTest
    @EnumSource(PipeMaterial.class)
    @DisplayName("Should have correct roughness for each material")
    void shouldHaveCorrectRoughness(PipeMaterial material) {
        // Given
        Pipe pipe = new Pipe(0.1, 10.0, material);

        // Then
        assertEquals(material.getRoughness(), pipe.getRoughness());
    }

    @Test
    @DisplayName("Should be immutable")
    void shouldBeImmutable() {
        // Given
        Pipe pipe = new Pipe(0.1, 10.0, PipeMaterial.PVC);

        // When - create new pipe with same values
        Pipe samePipe = new Pipe(0.1, 10.0, PipeMaterial.PVC);

        // Then
        assertAll(
                // Test value equality
                () -> assertEquals(pipe, samePipe),
                // Test hashCode consistency
                () -> assertEquals(pipe.hashCode(), samePipe.hashCode()),
                // Test state consistency
                () -> assertEquals(0.1, pipe.getDiameter()),
                () -> assertEquals(10.0, pipe.getLength()),
                () -> assertEquals(PipeMaterial.PVC, pipe.getMaterial()),
                () -> assertEquals(PipeMaterial.PVC.getRoughness(), pipe.getRoughness()));
    }

    @Test
    @DisplayName("Should reject invalid diameter")
    void shouldRejectInvalidDiameter() {
        assertThrows(IllegalArgumentException.class, () -> new Pipe(0.001, 10.0, PipeMaterial.PVC));

        assertThrows(IllegalArgumentException.class, () -> new Pipe(4.0, 10.0, PipeMaterial.PVC));
    }

    @Test
    @DisplayName("Should reject invalid length")
    void shouldRejectInvalidLength() {
        assertThrows(IllegalArgumentException.class, () -> new Pipe(0.1, 0.01, PipeMaterial.PVC));
    }

    @Test
    @DisplayName("Should reject null material")
    void shouldRejectNullMaterial() {
        assertThrows(IllegalArgumentException.class,
                () -> new Pipe(0.1, 10.0, null));
    }
}

package com.coffeecode.domain.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.constants.SimulationDefaults;
import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("PipeProperties Tests")
class PipePropertiesTest {

    @Test
    @DisplayName("Should create properties with valid parameters")
    void shouldCreatePropertiesWithValidParameters() {
        Distance length = Distance.of(100.0);
        Volume capacity = Volume.of(500.0);
        double diameter = 0.3;
        double roughness = 0.0002;

        PipeProperties properties = PipeProperties.builder()
                .length(length)
                .capacity(capacity)
                .diameter(diameter)
                .roughness(roughness)
                .build();

        assertEquals(length, properties.getLength());
        assertEquals(capacity, properties.getCapacity());
        assertEquals(diameter, properties.getDiameter());
        assertEquals(roughness, properties.getRoughness());
    }

    @Test
    @DisplayName("Should create properties with default values")
    void shouldCreatePropertiesWithDefaultValues() {
        Distance length = Distance.of(100.0);
        Volume capacity = Volume.of(500.0);

        PipeProperties properties = PipeProperties.of(length, capacity);

        assertEquals(length, properties.getLength());
        assertEquals(capacity, properties.getCapacity());
        assertEquals(SimulationDefaults.PIPE_DIAMETER, properties.getDiameter());
        assertEquals(SimulationDefaults.PIPE_ROUGHNESS, properties.getRoughness());
    }

    @Test
    @DisplayName("Should throw ValidationException for null length")
    void shouldThrowValidationExceptionForNullLength() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> PipeProperties.builder()
                        .capacity(Volume.of(500.0))
                        .build()
        );
        assertTrue(exception.getMessage().contains("Length cannot be null"));
    }

    @Test
    @DisplayName("Should throw ValidationException for null capacity")
    void shouldThrowValidationExceptionForNullCapacity() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> PipeProperties.builder()
                        .length(Distance.of(100.0))
                        .build()
        );
        assertTrue(exception.getMessage().contains("Capacity cannot be null"));
    }

    @Test
    @DisplayName("Should throw ValidationException for negative diameter")
    void shouldThrowValidationExceptionForNegativeDiameter() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> PipeProperties.builder()
                        .length(Distance.of(100.0))
                        .capacity(Volume.of(500.0))
                        .diameter(-1.0)
                        .build()
        );
        assertTrue(exception.getMessage().contains("Diameter must be positive"));
    }

    @Test
    @DisplayName("Should throw ValidationException for negative roughness")
    void shouldThrowValidationExceptionForNegativeRoughness() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> PipeProperties.builder()
                        .length(Distance.of(100.0))
                        .capacity(Volume.of(500.0))
                        .roughness(-0.001)
                        .build()
        );
        assertTrue(exception.getMessage().contains("Roughness cannot be negative"));
    }
}

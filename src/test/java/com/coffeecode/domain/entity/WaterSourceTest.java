package com.coffeecode.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.entities.NodeType;
import com.coffeecode.domain.entities.WaterSource;
import com.coffeecode.domain.values.Coordinate;
import com.coffeecode.domain.values.Volume;
import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("WaterSource Tests")
class WaterSourceTest {
    private Coordinate location;
    private Volume capacity;

    @BeforeEach
    void setUp() {
        location = Coordinate.of(0.0, 0.0);
        capacity = Volume.of(1000.0);
    }

    @Test
    @DisplayName("Should create water source with valid parameters")
    void shouldCreateWaterSourceWithValidParameters() {
        WaterSource source = WaterSource.builder()
            .name("Source1")
            .location(location)
            .capacity(capacity)
            .build();

        assertNotNull(source.getId());
        assertEquals("Source1", source.getName());
        assertEquals(location, source.getLocation());
        assertEquals(capacity, source.getCapacity());
        assertEquals(NodeType.SOURCE, source.getType());
    }

    @Test
    @DisplayName("Should throw ValidationException for null name")
    void shouldThrowValidationExceptionForNullName() {
        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> WaterSource.builder()
                .location(location)
                .capacity(capacity)
                .build()
        );
        assertTrue(exception.getMessage().contains("Source name cannot be empty"));
    }

    @Test
    @DisplayName("Should throw ValidationException for null location")
    void shouldThrowValidationExceptionForNullLocation() {
        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> WaterSource.builder()
                .name("Source1")
                .capacity(capacity)
                .build()
        );
        assertTrue(exception.getMessage().contains("Location cannot be null"));
    }

    @Test
    @DisplayName("Should throw ValidationException for null capacity")
    void shouldThrowValidationExceptionForNullCapacity() {
        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> WaterSource.builder()
                .name("Source1")
                .location(location)
                .build()
        );
        assertTrue(exception.getMessage().contains("Capacity cannot be null"));
    }

    @Test
    @DisplayName("Should generate unique IDs")
    void shouldGenerateUniqueIds() {
        WaterSource source1 = WaterSource.builder()
            .name("Source1")
            .location(location)
            .capacity(capacity)
            .build();

        WaterSource source2 = WaterSource.builder()
            .name("Source2")
            .location(location)
            .capacity(capacity)
            .build();

        assertNotEquals(source1.getId(), source2.getId());
    }
}

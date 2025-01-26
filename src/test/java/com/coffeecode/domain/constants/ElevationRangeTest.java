package com.coffeecode.domain.constants;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Elevation Range Tests")
class ElevationRangeTest {

    @Test
    @DisplayName("Should have valid elevation ranges")
    void shouldHaveValidElevationRanges() {
        assertTrue(ElevationRange.MIN.getValue() < ElevationRange.MAX.getValue()); // Minimum is less than maximum
        assertTrue(ElevationRange.MIN.getValue() >= -500); // Reasonable minimum
        assertTrue(ElevationRange.MAX.getValue() <= 9000); // Mount Everest ~8848m
    }
}

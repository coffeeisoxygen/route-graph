package com.coffeecode.domain.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("Distance Tests")
class DistanceTest {

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 100.0, Double.MAX_VALUE})
    @DisplayName("Should create valid distance")
    void shouldCreateValidDistance(double value) {
        Distance distance = Distance.of(value);
        assertEquals(value, distance.getValue());
    }

    @Test
    @DisplayName("Should throw ValidationException for negative distance")
    void shouldThrowValidationExceptionForNegativeDistance() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> Distance.of(-1.0)
        );
        assertEquals("Distance cannot be negative!", exception.getMessage());
    }

    @Test
    @DisplayName("Should implement equals and hashCode")
    void shouldImplementEqualsAndHashCode() {
        Distance distance1 = Distance.of(100.0);
        Distance distance2 = Distance.of(100.0);
        Distance distance3 = Distance.of(200.0);

        assertEquals(distance1, distance2);
        assertNotEquals(distance1, distance3);
        assertEquals(distance1.hashCode(), distance2.hashCode());
    }

    @Test
    @DisplayName("Should implement toString")
    void shouldImplementToString() {
        Distance distance = Distance.of(100.0);
        assertEquals("Distance(value=100.0)", distance.toString());
    }
}

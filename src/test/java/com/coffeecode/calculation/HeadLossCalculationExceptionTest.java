package com.coffeecode.calculation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HeadLoss Calculation Exception Tests")
class HeadLossCalculationExceptionTest {

    @Test
    @DisplayName("Should create exception with message")
    void shouldCreateExceptionWithMessage() {
        // Given
        String errorMessage = "Test error message";

        // When
        HeadLossCalculationException exception = new HeadLossCalculationException(errorMessage);

        // Then
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should create exception with message and cause")
    void shouldCreateExceptionWithMessageAndCause() {
        // Given
        String errorMessage = "Test error message";
        Throwable cause = new IllegalArgumentException("Root cause");

        // When
        HeadLossCalculationException exception = new HeadLossCalculationException(errorMessage, cause);

        // Then
        assertEquals(errorMessage, exception.getMessage());
        assertNotNull(exception.getCause());
        assertEquals(cause, exception.getCause());
    }
}

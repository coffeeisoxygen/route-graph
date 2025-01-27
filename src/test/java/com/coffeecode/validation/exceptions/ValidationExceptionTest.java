package com.coffeecode.validation.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ValidationException Tests")
class ValidationExceptionTest {

    @Test
    @DisplayName("Should create exception with message")
    void shouldCreateExceptionWithMessage() {
        String message = "Test error message";
        ValidationException exception = new ValidationException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("Should create null or empty exception")
    void shouldCreateNullOrEmptyException() {
        String fieldName = "testField";
        ValidationException exception = ValidationException.nullOrEmpty(fieldName);
        assertEquals(fieldName + " cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should create invalid range exception")
    void shouldCreateInvalidRangeException() {
        ValidationException exception = ValidationException.invalidRange("value", 0.0, 100.0);
        assertTrue(exception.getMessage().contains("must be between"));
        assertTrue(exception.getMessage().contains("0.000000"));
        assertTrue(exception.getMessage().contains("100.000000"));
    }
}

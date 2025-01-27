package com.coffeecode.validation.exceptions;

/**
 * Custom exception for validation errors in the water distribution network.
 * Provides specific error handling for domain validation failures.
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new validation exception with a message.
     *
     * @param message The error message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Creates a new validation exception with a message and cause.
     *
     * @param message The error message
     * @param cause The underlying cause
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a validation exception for null or empty values.
     *
     * @param fieldName The name of the invalid field
     * @return A new validation exception
     */
    public static ValidationException nullOrEmpty(String fieldName) {
        return new ValidationException(fieldName + " cannot be null or empty");
    }

    /**
     * Creates a validation exception for invalid range values.
     *
     * @param fieldName The name of the field
     * @param min The minimum allowed value
     * @param max The maximum allowed value
     * @return A new validation exception
     */
    public static ValidationException invalidRange(String fieldName, double min, double max) {
        return new ValidationException(
                String.format("%s must be between %f and %f", fieldName, min, max));
    }
}

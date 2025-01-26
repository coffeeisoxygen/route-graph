package com.coffeecode.domain.validation;

import com.coffeecode.domain.validation.exceptions.ValidationException;

public final class ArgumentValidator {

    private ArgumentValidator() {
    } // Prevent instantiation

    public static void notNull(Object value, String fieldName) {
        if (value == null) {
            throw new ValidationException(fieldName + " cannot be null");
        }
    }

    public static void notEmpty(String value, String fieldName) {
        notNull(value, fieldName);
        if (value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " cannot be empty");
        }
    }

    public static void notNegative(double value, String fieldName) {
        if (value < 0) {
            throw new ValidationException(fieldName + " cannot be negative");
        }
    }
}

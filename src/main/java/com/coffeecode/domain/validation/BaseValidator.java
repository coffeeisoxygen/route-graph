package com.coffeecode.domain.validation;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseValidator {

    protected ValidationResult validate(Runnable... validations) {
        List<String> errors = new ArrayList<>();

        for (Runnable validation : validations) {
            try {
                validation.run();
            } catch (ValidationException e) {
                errors.add(e.getMessage());
            }
        }

        return errors.isEmpty() ? ValidationResult.valid() : ValidationResult.invalid(errors);
    }

    protected void requireValid(ValidationResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(String.join(", ", result.getErrors()));
        }
    }

    protected void require(boolean condition, String message) {
        if (!condition) {
            throw new ValidationException(message);
        }
    }

    protected <T> T requireNonNull(T value, String message) {
        if (value == null) {
            throw new ValidationException(message);
        }
        return value;
    }
}

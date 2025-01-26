package com.coffeecode.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.coffeecode.validation.exceptions.ValidationException;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for validating objects using Jakarta Validation API.
 */
@Slf4j
public class ValidationUtils {

    private ValidationUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    /**
     * Validates an object and throws ValidationException if validation fails.
     *
     * @param object Object to validate
     * @param <T> Type of the object
     * @throws ValidationException If validation fails
     */
    public static <T> T validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed: ");
            for (ConstraintViolation<T> violation : violations) {
                errorMessage.append(violation.getPropertyPath())
                        .append(" ")
                        .append(violation.getMessage())
                        .append("; ");
            }
            log.info(errorMessage.toString());
            throw new ValidationException(errorMessage.toString());
        }
        return object;
    }
}

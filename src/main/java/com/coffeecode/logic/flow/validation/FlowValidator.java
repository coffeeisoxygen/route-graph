package com.coffeecode.logic.flow.validation;

import com.coffeecode.domain.entity.Pipe;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FlowValidator {

    private FlowValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static void validatePipe(Pipe pipe) {
        if (pipe == null) {
            throw new ValidationException("Pipe cannot be null");
        }
    }

    public static void validatePressure(double pressure) {
        if (pressure < 0) {
            throw new ValidationException("Pressure cannot be negative");
        }
    }
}

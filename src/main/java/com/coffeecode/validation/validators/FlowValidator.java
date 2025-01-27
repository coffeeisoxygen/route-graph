package com.coffeecode.validation.validators;

import com.coffeecode.domain.entities.Pipe;
import com.coffeecode.domain.values.PipeProperties;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FlowValidator {

    // Message constants for consistent validation
    private static final String PIPE_NULL = "Pipe cannot be null";
    private static final String PROPERTIES_NULL = "Pipe properties cannot be null";
    private static final String INVALID_DIAMETER = "Pipe diameter must be positive";
    private static final String INVALID_LENGTH = "Pipe length must be positive";
    private static final String INVALID_NODES = "Pipe must have source and destination";
    private static final String NEGATIVE_PRESSURE = "Pressure cannot be negative";

    private FlowValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static void validatePipe(Pipe pipe) {
        if (pipe == null) {
            throw new ValidationException(PIPE_NULL);
        }

        validatePipeProperties(pipe.getProperties());
        validatePipeNodes(pipe);
    }

    private static void validatePipeProperties(PipeProperties properties) {
        if (properties == null) {
            throw new ValidationException(PROPERTIES_NULL);
        }
        if (properties.getDiameter() <= 0) {
            throw new ValidationException(INVALID_DIAMETER);
        }
        if (properties.getLength().getValue() <= 0) {
            throw new ValidationException(INVALID_LENGTH);
        }
    }

    private static void validatePipeNodes(Pipe pipe) {
        if (pipe.getSource() == null || pipe.getDestination() == null) {
            throw new ValidationException(INVALID_NODES);
        }
    }

    public static void validatePressure(double pressure) {
        if (pressure < 0) {
            throw new ValidationException(NEGATIVE_PRESSURE);
        }
    }
}

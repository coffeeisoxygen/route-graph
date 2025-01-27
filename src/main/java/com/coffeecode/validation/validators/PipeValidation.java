package com.coffeecode.validation.validators;

import com.coffeecode.domain.entities.NetworkNode;
import com.coffeecode.domain.values.PipeProperties;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PipeValidation {

    private PipeValidation() {
    }

    public static void validateSource(NetworkNode source) {
        if (source == null) {
            throw new ValidationException("Source node cannot be null");
        }
    }

    public static void validateDestination(NetworkNode destination) {
        if (destination == null) {
            throw new ValidationException("Destination node cannot be null");
        }
    }

    public static void validateProperties(PipeProperties properties) {
        if (properties == null) {
            throw new ValidationException("Pipe properties cannot be null");
        }
    }

    public static void validateSameNode(NetworkNode source, NetworkNode destination) {
        if (source != null && destination != null && source.equals(destination)) {
            throw new ValidationException("Source and destination cannot be the same node");
        }
    }
}

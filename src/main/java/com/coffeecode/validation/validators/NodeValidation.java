package com.coffeecode.validation.validators;

import com.coffeecode.domain.entities.NodeType;
import com.coffeecode.domain.values.Coordinate;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class NodeValidation {

    private NodeValidation() {
    }

    public static void validateLocation(Coordinate location) {
        if (location == null) {
            throw new ValidationException("Location cannot be null");
        }
    }

    public static void validateType(NodeType type) {
        if (type == null) {
            throw new ValidationException("Node type cannot be null");
        }
    }
}

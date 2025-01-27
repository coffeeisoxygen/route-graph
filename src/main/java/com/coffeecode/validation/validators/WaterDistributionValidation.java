package com.coffeecode.validation.validators;

import com.coffeecode.domain.entities.NetworkNode;
import com.coffeecode.domain.entities.WaterSource;
import com.coffeecode.domain.values.pipe.PipeProperties;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class WaterDistributionValidation {

    private WaterDistributionValidation() {
    }

    public static void validateSource(WaterSource source) {
        if (source == null) {
            throw new ValidationException("Source cannot be null");
        }
    }

    public static void validateNode(NetworkNode node) {
        if (node == null) {
            throw new ValidationException("Node cannot be null");
        }
    }

    public static void validateConnection(NetworkNode from, NetworkNode to, PipeProperties properties) {
        validateNode(from);
        validateNode(to);
        if (properties == null) {
            throw new ValidationException("Pipe properties cannot be null");
        }
        if (from.equals(to)) {
            throw new ValidationException("Cannot connect node to itself");
        }
    }
}

package com.coffeecode.validation.specifications;

import com.coffeecode.domain.entities.NetworkNode;
import com.coffeecode.domain.values.Distance;
import com.coffeecode.domain.values.PipeProperties;
import com.coffeecode.domain.values.PipeType;
import com.coffeecode.validation.exceptions.ValidationException;

public interface PipeSpecification {

    // Physical limits
    double MIN_LENGTH_METERS = 0.1;
    double MAX_LENGTH_METERS = 10000.0;

    static void validatePipeProperties(PipeType type, double diameter, double roughness) {
        validateDiameter(type, diameter);
        validateRoughness(type, roughness);
    }

    static void validateDiameter(PipeType type, double diameter) {
        if (diameter < type.getMinDiameter() || diameter > type.getMaxDiameter()) {
            throw new ValidationException(String.format(
                    "Diameter must be between %.2f and %.2f for %s pipe",
                    type.getMinDiameter(), type.getMaxDiameter(), type.name()));
        }
    }

    static void validateRoughness(PipeType type, double roughness) {
        if (Math.abs(roughness - type.getRoughness()) > 1e-6) {
            throw new ValidationException(String.format(
                    "Roughness must be %.6f for %s pipe",
                    type.getRoughness(), type.name()));
        }
    }

    static void validateLength(Distance length) {
        if (length == null) {
            throw new ValidationException("Length cannot be null");
        }
        double meters = length.getValue();
        if (meters < MIN_LENGTH_METERS || meters > MAX_LENGTH_METERS) {
            throw new ValidationException(String.format(
                    "Length must be between %.1f and %.1f meters",
                    MIN_LENGTH_METERS, MAX_LENGTH_METERS));
        }
    }

    static void validateNetworkNodes(NetworkNode source, NetworkNode destination) {
        if (source == null) {
            throw new ValidationException("Source node cannot be null");
        }
        if (destination == null) {
            throw new ValidationException("Destination node cannot be null");
        }
        if (source.equals(destination)) {
            throw new ValidationException("Source and destination cannot be the same node");
        }
    }

    static void validatePipeConstruction(NetworkNode source, NetworkNode destination, PipeProperties properties) {
        validateNetworkNodes(source, destination);
        validateProperties(properties);
    }

    static void validateProperties(PipeProperties properties) {
        if (properties == null) {
            throw new ValidationException("Pipe properties cannot be null");
        }
        validateLength(properties.getLength());
        validatePipeProperties(properties.getType(), properties.getDiameter(), properties.getRoughness());
    }
}

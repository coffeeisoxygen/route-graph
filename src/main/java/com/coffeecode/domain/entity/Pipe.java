package com.coffeecode.domain.entity;

import java.util.UUID;

import com.coffeecode.domain.objects.PipeProperties;
import com.coffeecode.validation.ValidationUtils;
import com.coffeecode.validation.exceptions.ValidationException;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a Pipe entity in the network graph. A Pipe connects a source
 * NetworkNode to a destination NetworkNode and contains specific properties
 * defined in PipeProperties.
 *
 * <p>
 * This class is immutable and thread-safe.</p>
 *
 * <p>
 * Each Pipe instance is assigned a unique identifier (UUID) upon creation.</p>
 *
 *
 */
@Getter
@ToString
@EqualsAndHashCode
public class Pipe {

    private final UUID id;

    @NotNull(message = "Source node cannot be null")
    private final NetworkNode source;

    @NotNull(message = "Destination node cannot be null")
    private final NetworkNode destination;

    @NotNull(message = "Pipe properties cannot be null")
    private final PipeProperties properties;

    public Pipe(NetworkNode source, NetworkNode destination, PipeProperties properties) {
        this.id = UUID.randomUUID();
        this.source = source;
        this.destination = destination;
        this.properties = properties;
        validateSameNode();
        validate();
    }

    private void validate() {
        validateSameNode();
        ValidationUtils.validate(this);
    }

    private void validateSameNode() {
        throw new ValidationException("Source and destination cannot be the same node");
    }
}

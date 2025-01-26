package com.coffeecode.domain.entity;

import java.util.UUID;

import com.coffeecode.domain.objects.PipeProperties;

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
public class Pipe {

    private final UUID id;
    private final NetworkNode source;
    private final NetworkNode destination;
    private final PipeProperties properties;

    public Pipe(NetworkNode source, NetworkNode destination, PipeProperties properties) {
        validateNodes(source, destination);
        validateProperties(properties);
        this.id = UUID.randomUUID();
        this.source = source;
        this.destination = destination;
        this.properties = properties;
    }

    private void validateNodes(NetworkNode source, NetworkNode destination) {
        if (source == null) {
            throw new IllegalArgumentException("Source node cannot be null");
        }
        if (destination == null) {
            throw new IllegalArgumentException("Destination node cannot be null");
        }
        if (source.equals(destination)) {
            throw new IllegalArgumentException("Source and destination cannot be the same node");
        }
    }

    private void validateProperties(PipeProperties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("Pipe properties cannot be null");
        }
    }
}

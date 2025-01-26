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
        this.id = UUID.randomUUID();
        this.source = source;
        this.destination = destination;
        this.properties = properties;
    }
}

package com.coffeecode.domain.entity;

import java.util.UUID;

import com.coffeecode.domain.objects.PipeProperties;

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

    private Pipe(PipeBuilder builder) {
        PipeValidation.validateSource(builder.source);
        PipeValidation.validateDestination(builder.destination);
        PipeValidation.validateProperties(builder.properties);
        PipeValidation.validateSameNode(builder.source, builder.destination);

        this.id = UUID.randomUUID();
        this.source = builder.source;
        this.destination = builder.destination;
        this.properties = builder.properties;
    }

    public static PipeBuilder builder() {
        return new PipeBuilder();
    }

    public static class PipeBuilder {

        private NetworkNode source;
        private NetworkNode destination;
        private PipeProperties properties;

        public PipeBuilder source(NetworkNode source) {
            this.source = source;
            return this;
        }

        public PipeBuilder destination(NetworkNode destination) {
            this.destination = destination;
            return this;
        }

        public PipeBuilder properties(PipeProperties properties) {
            this.properties = properties;
            return this;
        }

        public Pipe build() {
            return new Pipe(this);
        }
    }
}

package com.coffeecode.domain.entities;

import java.util.UUID;

import com.coffeecode.domain.values.location.Distance;
import com.coffeecode.domain.values.pipe.PipeProperties;
import com.coffeecode.domain.values.water.WaterVolume;
import com.coffeecode.validation.specifications.PipeSpecification;

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
    private final NetworkNode source;
    private final NetworkNode destination;
    private final PipeProperties properties;

    private Pipe(PipeBuilder builder) {
        PipeSpecification.validatePipeConstruction(
                builder.source,
                builder.destination,
                builder.properties
        );

        this.id = UUID.randomUUID();
        this.source = builder.source;
        this.destination = builder.destination;
        this.properties = builder.properties;
    }

    public double getDiameter() {
        return properties.getDiameter();
    }

    public double getRoughness() {
        return properties.getRoughness();
    }

    public Distance getLength() {
        return properties.getLength();
    }

    public WaterVolume getCapacity() {
        return properties.getCapacity();
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

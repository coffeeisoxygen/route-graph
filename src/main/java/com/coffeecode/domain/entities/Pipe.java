package com.coffeecode.domain.entities;

import java.util.UUID;

import com.coffeecode.domain.values.pipe.PipeProperties;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.Value;

@Value
public class Pipe {
    UUID id;
    NetworkNode source;
    NetworkNode destination;
    PipeProperties properties;

    private Pipe(PipeBuilder builder) {
        validatePipe(builder);
        this.id = UUID.randomUUID();
        this.source = builder.source;
        this.destination = builder.destination;
        this.properties = builder.properties;
    }

    private void validatePipe(PipeBuilder builder) {
        if (builder.source == null) {
            throw ValidationException.nullOrEmpty("Source node");
        }
        if (builder.destination == null) {
            throw ValidationException.nullOrEmpty("Destination node");
        }
        if (builder.properties == null) {
            throw ValidationException.nullOrEmpty("Pipe properties");
        }
        if (builder.source.equals(builder.destination)) {
            throw new ValidationException("Source and destination cannot be the same node");
        }
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

package com.coffeecode.domain.values;

import com.coffeecode.domain.constants.SimulationDefaults;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class PipeProperties {

    private final Distance length;
    private final Volume capacity;
    private final double diameter;
    private final double roughness;

    private PipeProperties(PipePropertiesBuilder builder) {
        validateLength(builder.length);
        validateCapacity(builder.capacity);
        validateDiameter(builder.diameter);
        validateRoughness(builder.roughness);

        this.length = builder.length;
        this.capacity = builder.capacity;
        this.diameter = builder.diameter;
        this.roughness = builder.roughness;
    }

    public static PipePropertiesBuilder builder() {
        return new PipePropertiesBuilder();
    }

    public static PipeProperties of(Distance length, Volume capacity) {
        return builder()
                .length(length)
                .capacity(capacity)
                .diameter(SimulationDefaults.PIPE_DIAMETER)
                .roughness(SimulationDefaults.PIPE_ROUGHNESS)
                .build();
    }

    private static void validateLength(Distance length) {
        if (length == null) {
            throw new ValidationException("Length cannot be null");
        }
    }

    private static void validateCapacity(Volume capacity) {
        if (capacity == null) {
            throw new ValidationException("Capacity cannot be null");
        }
    }

    private static void validateDiameter(double diameter) {
        if (diameter <= 0) {
            throw new ValidationException("Diameter must be positive");
        }
    }

    private static void validateRoughness(double roughness) {
        if (roughness < 0) {
            throw new ValidationException("Roughness cannot be negative");
        }
    }

    public static class PipePropertiesBuilder {

        private Distance length;
        private Volume capacity;
        private double diameter = SimulationDefaults.PIPE_DIAMETER;
        private double roughness = SimulationDefaults.PIPE_ROUGHNESS;

        public PipePropertiesBuilder length(Distance length) {
            this.length = length;
            return this;
        }

        public PipePropertiesBuilder capacity(Volume capacity) {
            this.capacity = capacity;
            return this;
        }

        public PipePropertiesBuilder diameter(double diameter) {
            this.diameter = diameter;
            return this;
        }

        public PipePropertiesBuilder roughness(double roughness) {
            this.roughness = roughness;
            return this;
        }

        public PipeProperties build() {
            return new PipeProperties(this);
        }
    }
}

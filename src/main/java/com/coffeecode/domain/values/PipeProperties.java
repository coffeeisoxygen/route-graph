package com.coffeecode.domain.values;

import com.coffeecode.domain.constants.PhysicalConstants;
import com.coffeecode.validation.specifications.PipeSpecification;

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
    private final PipeType type;

    private PipeProperties(PipePropertiesBuilder builder) {
        // Validate using specification
        PipeSpecification.validateLength(builder.length);
        PipeSpecification.validatePipeProperties(builder.type, builder.diameter, builder.roughness);

        this.length = builder.length;
        this.capacity = builder.capacity;
        this.diameter = builder.diameter;
        this.type = builder.type;
        this.roughness = builder.type.getRoughness(); // Always use type's roughness
    }

    // Factory methods
    public static PipeProperties of(Distance length, Volume capacity, PipeType type) {
        return builder()
                .length(length)
                .capacity(capacity)
                .type(type)
                .diameter(type.getMinDiameter()) // Use minimum diameter as default
                .build();
    }

    public static PipePropertiesBuilder builder() {
        return new PipePropertiesBuilder();
    }

    // Add calculations
    public double calculateFluidVelocity(double flowRate) {
        double area = Math.PI * Math.pow(diameter / 2, 2);
        return flowRate / area;
    }

    public double calculatePressureLoss(double flowRate, double temperature) {
        double velocity = calculateFluidVelocity(flowRate);
        double reynoldsNumber = (velocity * diameter) / PhysicalConstants.Water.KINEMATIC_VISCOSITY;
        double frictionFactor = calculateFrictionFactor(reynoldsNumber);

        return (frictionFactor * length.getValue() * Math.pow(velocity, 2))
                / (2 * diameter * PhysicalConstants.Environment.GRAVITY); // head loss in meters
    }

    public Volume calculateCapacity() {
        double area = Math.PI * Math.pow(diameter / 2, 2);
        return Volume.of(area * length.getValue());
    }

    private double calculateFrictionFactor(double reynoldsNumber) {
        // Swamee-Jain equation for friction factor
        double relativeRoughness = roughness / diameter;
        return 0.25 / Math.pow(Math.log10(relativeRoughness / 3.7 + 5.74 / Math.pow(reynoldsNumber, 0.9)), 2);
    }

    public static class PipePropertiesBuilder {

        private Distance length;
        private Volume capacity;
        private double diameter;
        private double roughness;
        private PipeType type = PipeType.PVC; // Default type

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

        public PipePropertiesBuilder type(PipeType type) {
            this.type = type;
            this.roughness = type.getRoughness(); // Set roughness based on type
            return this;
        }

        public PipeProperties build() {
            return new PipeProperties(this);
        }
    }
}

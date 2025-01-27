package com.coffeecode.domain.values.pipe;

import com.coffeecode.domain.constants.PhysicalConstants;
import com.coffeecode.domain.values.location.Distance;
import com.coffeecode.domain.values.water.WaterVolume;
import com.coffeecode.validation.specifications.PipeSpecification;
import com.coffeecode.validation.validators.HydraulicValidator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString
@EqualsAndHashCode
@Slf4j
public class PipeProperties {

    private final Distance length;
    private final WaterVolume capacity;
    private final double diameter;
    private final double roughness;
    private final PipeMaterial type;

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
    public static PipeProperties of(Distance length, WaterVolume capacity, PipeMaterial type) {
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
        HydraulicValidator.validatePositiveValue("Flow rate", flowRate);

        double area = Math.PI * Math.pow(diameter / 2, 2);
        double velocity = flowRate / area;

        log.debug("Calculated fluid velocity: {} m/s", velocity);
        return velocity;
    }

    public double calculatePressureLoss(double flowRate) {
        double velocity = calculateFluidVelocity(flowRate);
        double reynolds = calculateReynoldsNumber(velocity);
        double friction = calculateFrictionFactor(reynolds);

        return calculateHeadLoss(friction, velocity);
    }

    private double calculateReynoldsNumber(double velocity) {
        return (velocity * diameter)
                / PhysicalConstants.Water.KINEMATIC_VISCOSITY;
    }

    private double calculateHeadLoss(double friction, double velocity) {
        return (friction * length.getValue() * Math.pow(velocity, 2))
                / (2 * diameter * PhysicalConstants.Environment.GRAVITY);
    }

    public WaterVolume calculateCapacity() {
        double area = Math.PI * Math.pow(diameter / 2, 2);
        return WaterVolume.of(area * length.getValue());
    }

    private double calculateFrictionFactor(double reynoldsNumber) {
        // Swamee-Jain equation for friction factor
        double relativeRoughness = roughness / diameter;
        return 0.25 / Math.pow(Math.log10(relativeRoughness / 3.7 + 5.74 / Math.pow(reynoldsNumber, 0.9)), 2);
    }

    public static class PipePropertiesBuilder {

        private Distance length;
        private WaterVolume capacity;
        private double diameter;
        private double roughness;
        private PipeMaterial type = PipeMaterial.PVC; // Default type

        public PipePropertiesBuilder length(Distance length) {
            this.length = length;
            return this;
        }

        public PipePropertiesBuilder capacity(WaterVolume capacity) {
            this.capacity = capacity;
            return this;
        }

        public PipePropertiesBuilder diameter(double diameter) {
            this.diameter = diameter;
            return this;
        }

        public PipePropertiesBuilder type(PipeMaterial type) {
            this.type = type;
            this.roughness = type.getRoughness(); // Set roughness based on type
            return this;
        }

        public PipeProperties build() {
            return new PipeProperties(this);
        }
    }
}

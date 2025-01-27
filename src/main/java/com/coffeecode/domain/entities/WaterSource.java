package com.coffeecode.domain.entities;

import com.coffeecode.domain.constants.OperationalLimits;
import com.coffeecode.domain.values.location.Elevation;
import com.coffeecode.domain.values.water.WaterFlow;
import com.coffeecode.domain.values.water.WaterVolume;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WaterSource extends NetworkNode {

    private final String name;
    private final WaterVolume capacity;
    private final WaterFlow flowRate;
    private final Elevation elevation;

    private WaterSource(WaterSourceBuilder builder) {
        super(builder);
        validateSourceProperties(builder);

        this.name = builder.name;
        this.capacity = builder.capacity;
        this.flowRate = builder.flowRate;
        this.elevation = builder.elevation != null
                ? builder.elevation : Elevation.of(OperationalLimits.Elevation.DEFAULT);
    }

    private void validateSourceProperties(WaterSourceBuilder builder) {
        validateName(builder.name);
        validateCapacity(builder.capacity);
        validateFlow(builder.flowRate);
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw ValidationException.nullOrEmpty("Source name");
        }
        if (name.length() < OperationalLimits.Source.MIN_NAME_LENGTH
                || name.length() > OperationalLimits.Source.MAX_NAME_LENGTH) {
            throw ValidationException.invalidRange("Source name length",
                    OperationalLimits.Source.MIN_NAME_LENGTH,
                    OperationalLimits.Source.MAX_NAME_LENGTH);
        }
    }

    private void validateCapacity(WaterVolume capacity) {
        if (capacity == null) {
            throw ValidationException.nullOrEmpty("Source capacity");
        }
    }

    private void validateFlow(WaterFlow flowRate) {
        if (flowRate == null) {
            throw ValidationException.nullOrEmpty("Flow rate");
        }
    }

    public static WaterSourceBuilder builder() {
        return new WaterSourceBuilder();
    }

    public static class WaterSourceBuilder extends AbstractNodeBuilder<WaterSourceBuilder> {

        private String name;
        private WaterVolume capacity;
        private WaterFlow flowRate;
        private Elevation elevation;

        @Override
        public WaterSource build() {
            this.type(NodeType.SOURCE);
            return new WaterSource(this);
        }

        public WaterSourceBuilder name(String name) {
            this.name = name;
            return this;
        }

        public WaterSourceBuilder capacity(WaterVolume capacity) {
            this.capacity = capacity;
            return this;
        }

        public WaterSourceBuilder flowRate(WaterFlow flowRate) {
            this.flowRate = flowRate;
            return this;
        }

        public WaterSourceBuilder elevation(Elevation elevation) {
            this.elevation = elevation;
            return this;
        }
    }
}

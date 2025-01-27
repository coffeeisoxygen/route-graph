package com.coffeecode.domain.entities;

import com.coffeecode.domain.values.water.WaterVolume;
import com.coffeecode.validation.validators.WaterSourceValidation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WaterSource extends NetworkNode {

    private final String name;
    private final WaterVolume capacity;

    private WaterSource(WaterSourceBuilder builder) {
        super(builder);
        WaterSourceValidation.validateName(builder.name);
        WaterSourceValidation.validateCapacity(builder.capacity);
        this.name = builder.name;
        this.capacity = builder.capacity;
    }

    public static WaterSourceBuilder builder() {
        return new WaterSourceBuilder();
    }

    public static class WaterSourceBuilder extends AbstractNodeBuilder<WaterSourceBuilder> {

        private String name;
        private WaterVolume capacity;

        public WaterSourceBuilder name(String name) {
            this.name = name;
            return this;
        }

        public WaterSourceBuilder capacity(WaterVolume capacity) {
            this.capacity = capacity;
            return this;
        }

        @Override
        public WaterSource build() {
            this.type(NodeType.SOURCE);
            return new WaterSource(this);
        }
    }
}

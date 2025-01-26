package com.coffeecode.domain.entity;

import com.coffeecode.domain.entity.validation.WaterSourceValidation;
import com.coffeecode.domain.objects.Volume;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WaterSource extends NetworkNode {

    private final String name;
    private final Volume capacity;

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
        private Volume capacity;

        public WaterSourceBuilder name(String name) {
            this.name = name;
            return this;
        }

        public WaterSourceBuilder capacity(Volume capacity) {
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

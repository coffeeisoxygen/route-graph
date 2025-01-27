package com.coffeecode.domain.entities;

import com.coffeecode.domain.values.water.WaterDemand;
import com.coffeecode.validation.validators.CustomerValidation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a customer node in the water distribution network.
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WaterCustomer extends NetworkNode {

    private final String name;
    private final WaterDemand waterDemand;

    private WaterCustomer(CustomerBuilder builder) {
        super(builder);
        CustomerValidation.validateName(builder.name);
        CustomerValidation.validateWaterDemand(builder.waterDemand);
        this.name = builder.name;
        this.waterDemand = builder.waterDemand;
    }

    public static CustomerBuilder builder() {
        return new CustomerBuilder();
    }

    public static class CustomerBuilder extends AbstractNodeBuilder<CustomerBuilder> {

        private String name;
        private WaterDemand waterDemand;

        public CustomerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CustomerBuilder waterDemand(WaterDemand waterDemand) {
            this.waterDemand = waterDemand;
            return this;
        }

        @Override
        public WaterCustomer build() {
            this.type(NodeType.CUSTOMER); // Set type automatically
            return new WaterCustomer(this);
        }
    }
}

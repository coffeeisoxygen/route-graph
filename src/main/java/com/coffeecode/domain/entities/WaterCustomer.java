package com.coffeecode.domain.entities;

import com.coffeecode.domain.constants.OperationalLimits;
import com.coffeecode.domain.values.water.WaterDemand;
import com.coffeecode.validation.exceptions.ValidationException;

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
        validateCustomerProperties(builder);

        this.name = builder.name;
        this.waterDemand = builder.waterDemand;
    }

    private void validateCustomerProperties(CustomerBuilder builder) {
        validateName(builder.name);
        validateDemand(builder.waterDemand);
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw ValidationException.nullOrEmpty("Customer name");
        }
        if (name.length() < OperationalLimits.Customer.MIN_NAME_LENGTH ||
                name.length() > OperationalLimits.Customer.MAX_NAME_LENGTH) {
            throw ValidationException.invalidRange("Customer name length",
                    OperationalLimits.Customer.MIN_NAME_LENGTH,
                    OperationalLimits.Customer.MAX_NAME_LENGTH);
        }
    }

    private void validateDemand(WaterDemand demand) {
        if (demand == null) {
            throw ValidationException.nullOrEmpty("Water demand");
        }
        double value = demand.getValue();
        if (value < OperationalLimits.Customer.MIN_DEMAND ||
                value > OperationalLimits.Customer.MAX_DEMAND) {
            throw ValidationException.invalidRange("Customer demand",
                    OperationalLimits.Customer.MIN_DEMAND,
                    OperationalLimits.Customer.MAX_DEMAND);
        }
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

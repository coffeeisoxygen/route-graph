package com.coffeecode.domain.entity;

import com.coffeecode.domain.objects.WaterDemand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a customer node in the water distribution network.
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Customer extends NetworkNode {

    @NotBlank(message = "Customer name cannot be empty")
    private final String name;

    @NotNull(message = "Water demand cannot be null")
    private final WaterDemand waterDemand;

    private Customer(CustomerBuilder builder) {
        super(builder);
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
        public Customer build() {
            this.type(NodeType.CUSTOMER); // Set type automatically
            Customer customer = new Customer(this);
            return (Customer) validate(customer);
        }
    }
}

package com.coffeecode.domain.entity;

import com.coffeecode.domain.objects.Coordinate;
import com.coffeecode.domain.objects.WaterDemand;
import com.coffeecode.validation.ValidationUtils;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a customer in the network node system. This class extends the
 * {@link NetworkNode} class and includes additional attributes specific to a
 * customer, such as name and water demand.
 *
 * <p>
 * The {@code Customer} class is immutable and thread-safe.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 * <pre>
 * {@code
 * Coordinate location = new Coordinate(10, 20);
 * WaterDemand waterDemand = new WaterDemand(100);
 * Customer customer = new Customer("John Doe", location, waterDemand);
 * }
 * </pre>
 *
 * @see NetworkNode
 * @see Coordinate
 * @see WaterDemand
 *
 *
 */
@Getter
@ToString(callSuper = true, includeFieldNames = true)
@EqualsAndHashCode(callSuper = true)
public class Customer extends NetworkNode {

    @NotBlank(message = "Customer name cannot be empty")
    private final String name;

    @NotNull(message = "Water demand cannot be null")
    private final WaterDemand waterDemand;

    public Customer(String name, Coordinate location, WaterDemand waterDemand) {
        super(location, NodeType.CUSTOMER);
        this.name = name;
        this.waterDemand = waterDemand;
        validate();
    }

    private void validate() {
        ValidationUtils.validate(this);
    }
}

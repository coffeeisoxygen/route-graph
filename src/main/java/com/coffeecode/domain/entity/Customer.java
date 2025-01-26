package com.coffeecode.domain.entity;

import com.coffeecode.domain.objects.Coordinate;
import com.coffeecode.domain.objects.WaterDemand;

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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Customer extends NetworkNode {

    private final String name;
    private final WaterDemand waterDemand;

    public Customer(String name, Coordinate location, WaterDemand waterDemand) {
        super(location, NodeType.CUSTOMER);
        if (location == null) {
            throw new NullPointerException("Location cannot be null");
        }
        if (name == null || name.isEmpty()) {
            throw new NullPointerException("Customer name cannot be null or empty");
        }
        if (waterDemand == null) {
            throw new NullPointerException("Water demand cannot be null");
        }

        this.name = name;
        this.waterDemand = waterDemand;
    }
}

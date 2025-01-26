package com.coffeecode.domain.entity;

import com.coffeecode.domain.objects.Coordinate;
import com.coffeecode.domain.objects.WaterDemand;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Customer extends NetworkNode {

    private final String name;
    private final WaterDemand waterDemand;

    public Customer(String name, Coordinate location, WaterDemand waterDemand) {
        super(location, NodeType.CUSTOMER);
        this.name = name;
        this.waterDemand = waterDemand;
    }
}

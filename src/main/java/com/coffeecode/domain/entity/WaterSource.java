package com.coffeecode.domain.entity;

import com.coffeecode.domain.objects.Coordinate;
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

    public WaterSource(String name, Coordinate location, Volume capacity) {
        super(location, NodeType.SOURCE);
        this.name = name;
        this.capacity = capacity;
    }
}

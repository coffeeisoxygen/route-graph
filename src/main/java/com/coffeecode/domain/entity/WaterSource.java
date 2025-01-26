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
        validateName(name);
        validateCapacity(capacity);
        this.name = name;
        this.capacity = capacity;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Source name cannot be null or empty");
        }
    }

    private void validateCapacity(Volume capacity) {
        if (capacity == null) {
            throw new IllegalArgumentException("Capacity cannot be null");
        }
    }
}

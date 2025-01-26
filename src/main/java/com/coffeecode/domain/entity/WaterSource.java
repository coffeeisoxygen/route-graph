package com.coffeecode.domain.entity;

import com.coffeecode.domain.objects.Coordinate;
import com.coffeecode.domain.objects.Volume;
import com.coffeecode.validation.ValidationUtils;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WaterSource extends NetworkNode {

    @NotBlank(message = "Source name cannot be empty")
    private final String name;

    @NotNull(message = "Capacity cannot be null")
    private final Volume capacity;

    public WaterSource(String name, Coordinate location, Volume capacity) {
        super(location, NodeType.SOURCE);
        this.name = name;
        this.capacity = capacity;
        validate();
    }

    private void validate() {
        ValidationUtils.validate(this);
    }
}

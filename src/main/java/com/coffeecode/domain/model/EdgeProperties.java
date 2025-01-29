package com.coffeecode.domain.model;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EdgeProperties {
    @Positive
    private final Double bandwidth;
    @PositiveOrZero
    private final Double latency;
    private final Double cost;

    public double calculateWeight() {
        return cost != null ? cost : latency;
    }
}

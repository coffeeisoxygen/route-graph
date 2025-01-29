package com.coffeecode.domain.properties;

import javax.validation.constraints.Min;

import lombok.Value;

@Value
public class EdgeProperties implements NetworkProperties {
    @Min(0)
    double bandwidth;

    @Min(0)
    double latency;

    @Override
    public boolean isValid() {
        return bandwidth >= 0 && latency >= 0;
    }
}

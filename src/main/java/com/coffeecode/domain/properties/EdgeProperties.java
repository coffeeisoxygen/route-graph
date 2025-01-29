package com.coffeecode.domain.properties;

import javax.validation.constraints.Min;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EdgeProperties implements NetworkProperties {
    @Builder.Default
    @Min(0)
    double bandwidth = 100.0;

    @Builder.Default
    @Min(0)
    double latency = 10.0;

    @Override
    public boolean isValid() {
        return bandwidth >= 0 && latency >= 0;
    }
}

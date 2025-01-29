package com.coffeecode.domain.node.properties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ServerNodeProperties {
    @NotNull
    @Positive
    private final Integer capacity;
    @NotNull
    @Positive
    private final Double processingPower;
    private final Integer maxConnections;

    public boolean isValid() {
        return capacity != null &&
                capacity > 0 &&
                processingPower != null &&
                processingPower > 0 &&
                (maxConnections == null || maxConnections > 0);
    }
}

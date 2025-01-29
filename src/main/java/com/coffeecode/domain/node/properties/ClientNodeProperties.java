package com.coffeecode.domain.node.properties;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.coffeecode.domain.node.base.NodeType;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClientNodeProperties extends BaseNodeProperties {
    @Positive
    private final Double dataRate;
    private final Double maxBandwidth;

    @Builder
    public ClientNodeProperties(UUID id, String name, String description,
            @NotNull @Positive Double dataRate,
            Double maxBandwidth) {
        super(id, name, description, NodeType.CLIENT, true);
        this.dataRate = dataRate;
        this.maxBandwidth = maxBandwidth;
    }

    @Override
    public boolean isValid() {
        return super.isValid()
                && dataRate != null
                && dataRate > 0;
    }
}

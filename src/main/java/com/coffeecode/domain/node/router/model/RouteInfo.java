package com.coffeecode.domain.node.router.model;

import com.coffeecode.domain.model.NetworkIdentity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RouteInfo {
    NetworkIdentity nextHop;
    double metric;
    @Builder.Default
    long timestamp = System.currentTimeMillis();

    public boolean isValid() {
        return nextHop != null && metric >= 0;
    }
}

package com.coffeecode.domain.node.impl;

import com.coffeecode.domain.model.NetworkIdentity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RouteInfo {
    NetworkIdentity nextHop;
    double metric;
    long timestamp;

    public boolean isValid() {
        return nextHop != null && metric >= 0;
    }
}

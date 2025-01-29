package com.coffeecode.domain.node.client.model;

import com.coffeecode.domain.model.NetworkIdentity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NetworkRequest {
    NetworkIdentity target;
    RequestType type;
    @Builder.Default
    long timestamp = System.currentTimeMillis();
    @Builder.Default
    RequestStatus status = RequestStatus.PENDING;

    public boolean isValid() {
        return target != null && type != null;
    }
}

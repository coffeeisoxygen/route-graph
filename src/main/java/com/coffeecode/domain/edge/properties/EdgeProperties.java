package com.coffeecode.domain.edge.properties;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EdgeProperties {
    @Positive
    private final Double bandwidth; // Required bandwidth in Mbps
    @PositiveOrZero
    private final Double latency; // Latency in milliseconds
    @PositiveOrZero
    private final Double jitter; // Jitter in milliseconds
    @PositiveOrZero
    private final Double packetLoss; // Packet loss rate (0-100%)
    @Positive
    private final Integer mtu; // Maximum Transmission Unit in bytes
    @PositiveOrZero
    private final Double cost; // Cost metric for routing decisions

    public boolean isValid() {
        return bandwidth != null && bandwidth > 0
                && latency != null && latency >= 0
                && (jitter == null || jitter >= 0)
                && (packetLoss == null || (packetLoss >= 0 && packetLoss <= 100))
                && (mtu == null || mtu > 0)
                && (cost == null || cost >= 0);
    }
}

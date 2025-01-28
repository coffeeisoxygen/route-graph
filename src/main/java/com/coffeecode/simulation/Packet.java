package com.coffeecode.simulation;

import com.coffeecode.core.Node;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class Packet {
    private final String id;
    private final Node source;
    private final Node destination;
    private final double size;
    private final long creationTime;

    @Setter
    private PacketStatus status;

    public enum PacketStatus {
        CREATED,
        IN_TRANSIT,
        DELIVERED,
        FAILED
    }
}

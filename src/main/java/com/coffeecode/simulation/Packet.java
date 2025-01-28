package com.coffeecode.simulation;

import com.coffeecode.core.Node;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Packet {
    private String id;
    private Node source;
    private Node destination;
    private double size;
    private PacketStatus status;
    private long creationTime;

    public enum PacketStatus {
        CREATED,
        IN_TRANSIT,
        DELIVERED,
        FAILED
    }

    public void setStatus(PacketStatus status) {

        this.status = status;

    }

    public PacketStatus getStatus() {

        return status;

    }

}

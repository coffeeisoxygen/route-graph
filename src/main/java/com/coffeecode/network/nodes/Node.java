package com.coffeecode.network.nodes;

import java.util.UUID;

import com.coffeecode.location.coordinates.api.Coordinates;
import com.coffeecode.location.elevations.model.Elevation;

import lombok.Getter;
import lombok.NonNull;

@Getter
public abstract class Node {
    private final UUID id;
    private final String name;
    private final Coordinates coordinates;
    private final Elevation elevation;
    private final NodeType type;

    protected Node(
            @NonNull String name,
            @NonNull Coordinates coordinates,
            @NonNull Elevation elevation,
            @NonNull NodeType type) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.coordinates = coordinates;
        this.elevation = elevation;
        this.type = type;
    }

    public enum NodeType {
        SOURCE, DEMAND
    }
}

package com.coffeecode.domain.entity;

import java.util.UUID;

import com.coffeecode.domain.objects.Coordinate;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Abstract base class for network nodes with secure validation and
 * immutability.
 */
@Getter
@ToString
@EqualsAndHashCode
public abstract class NetworkNode {

    private final UUID id;

    @NotNull(message = "Location cannot be null")
    private final Coordinate location;

    @NotNull(message = "Node type cannot be null")
    private final NodeType type;

    /**
     * Protected constructor to prevent instantiation from outside the package.
     *
     * @param location must not be null
     * @param type must not be null
     */
    protected NetworkNode(final Coordinate location, final NodeType type) {
        this.id = UUID.randomUUID();
        this.location = location;
        this.type = type;
    }

    public enum NodeType {
        SOURCE, CUSTOMER, JUNCTION
    }
}

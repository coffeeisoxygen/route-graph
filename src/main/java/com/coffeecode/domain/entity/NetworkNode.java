package com.coffeecode.domain.entity;

import java.util.UUID;

import com.coffeecode.domain.objects.Coordinate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a node in a network with a unique identifier, location, and type.
 * This is an abstract class that should be extended by specific types of
 * network nodes.
 *
 * <p>
 * Each NetworkNode has:
 * <ul>
 * <li>A unique identifier (UUID)</li>
 * <li>A location represented by a {@link Coordinate}</li>
 * <li>A type represented by {@link NodeType}</li>
 * </ul>
 *
 * <p>
 * The {@link NodeType} enum defines the possible types of nodes:
 * <ul>
 * <li>{@code SOURCE} - Represents a source node</li>
 * <li>{@code CUSTOMER} - Represents a customer node</li>
 * <li>{@code JUNCTION} - Represents a junction node</li>
 * </ul>
 *
 * <p>
 * This class is immutable and thread-safe.
 *
 * @param location the coordinate location of the node
 * @param type the type of the node
 */
@Getter
@ToString
@EqualsAndHashCode
public abstract class NetworkNode {

    private final UUID id;
    private final Coordinate location;
    private final NodeType type;

    protected NetworkNode(Coordinate location, NodeType type) {
        this.id = UUID.randomUUID();
        this.location = location;
        this.type = type;
    }

    public enum NodeType {
        SOURCE, CUSTOMER, JUNCTION
    }
}

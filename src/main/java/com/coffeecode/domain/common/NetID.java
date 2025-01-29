package com.coffeecode.domain.common;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.coffeecode.domain.validation.ValidationException;

import lombok.NonNull;
import lombok.Value;

/**
 * Immutable network identifier with auto-generated name capability.
 * Thread-safe implementation for unique network entity identification.
 */
@Value
public class NetID {
    private static final Map<NetNodeType, AtomicInteger> NAME_SEQUENCES = new ConcurrentHashMap<>();

    @NonNull
    UUID id;
    @NonNull
    String name;

    private NetID(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Creates new NetID with auto-generated name based on node type
     *
     * @param nodeType The network node type
     * @return New NetID instance
     * @throws ValidationException if nodeType is null
     */
    public static NetID create(NetNodeType nodeType) {
        if (nodeType == null) {
            throw new ValidationException("NodeType cannot be null");
        }
        return new NetID(
                UUID.randomUUID(),
                generateName(nodeType));
    }

    /**
     * Legacy support for string-based type creation
     *
     * @deprecated Use {@link #create(NetNodeType)} instead
     */
    @Deprecated(since = "1.0", forRemoval = true)
    public static NetID create(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new ValidationException("Type cannot be null or empty");
        }
        try {
            return create(NetNodeType.valueOf(type.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid node type: " + type);
        }
    }

    /**
     * Creates NetID with specific id and name
     *
     * @param id   UUID for the network entity
     * @param name Name for the network entity
     * @return New NetID instance
     * @throws ValidationException if id or name is invalid
     */
    public static NetID of(UUID id, String name) {
        if (id == null) {
            throw new ValidationException("ID cannot be null");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name cannot be null or empty");
        }
        return new NetID(id, name);
    }

    private static String generateName(NetNodeType nodeType) {
        AtomicInteger sequence = NAME_SEQUENCES.computeIfAbsent(
                nodeType, k -> new AtomicInteger(0));
        return String.format("%s-%d",
                nodeType.getNodePrefix(),
                sequence.incrementAndGet());
    }

    public NetNodeType getType() {
        return NetNodeType.valueOf(name.split("-")[0].toUpperCase());
    }
}

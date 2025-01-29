package com.coffeecode.domain.model;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Builder;
import lombok.Value;

@Value
public class NetworkIdentity {
    private static final ConcurrentHashMap<NodeType, AtomicInteger> SEQUENCES = new ConcurrentHashMap<>();

    UUID id;
    String name;
    NodeType type;

    @Builder
    private NetworkIdentity(UUID id, String name, NodeType type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        this.id = id != null ? id : UUID.randomUUID();
        this.name = name != null ? name : generateName(type);
        this.type = type;
    }

    private static String generateName(NodeType type) {
        AtomicInteger sequence = SEQUENCES.computeIfAbsent(type, k -> new AtomicInteger(0));
        return String.format("%s-%03d", type.name(), sequence.incrementAndGet());
    }

    public static NetworkIdentity create(NodeType router) {
        return NetworkIdentity.builder()
                .type(router)
                .build();
    }
}

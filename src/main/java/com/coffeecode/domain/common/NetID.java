package com.coffeecode.domain.common;

import java.util.UUID;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class NetID {
    @NonNull
    UUID id;
    @NonNull
    String name;
    String description;

    public static NetID create(String type) {
        return NetID.builder()
                .id(UUID.randomUUID())
                .name(NameGenerator.generateName(type))
                .build();
    }

    public static NetID create(String type, String description) {
        return NetID.builder()
                .id(UUID.randomUUID())
                .name(NameGenerator.generateName(type))
                .description(description)
                .build();
    }
}

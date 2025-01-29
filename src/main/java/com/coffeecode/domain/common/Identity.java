package com.coffeecode.domain.common;

import java.util.UUID;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Identity {
    @NonNull
    UUID id;
    @NonNull
    String name;
    String description;

    public static Identity create(String type) {
        return Identity.builder()
                .id(UUID.randomUUID())
                .name(NameGenerator.generateName(type))
                .build();
    }

    public static Identity create(String type, String description) {
        return Identity.builder()
                .id(UUID.randomUUID())
                .name(NameGenerator.generateName(type))
                .description(description)
                .build();
    }
}

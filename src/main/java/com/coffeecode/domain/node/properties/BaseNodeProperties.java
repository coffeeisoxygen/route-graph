package com.coffeecode.domain.node.properties;

import java.util.UUID;
import javax.validation.constraints.NotNull;

import com.coffeecode.domain.node.base.NodeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseNodeProperties {
    @NotNull
    private final UUID id;
    private final String name;
    private final String description;
    @NotNull
    private final NodeType type;
    private final boolean active;

    @Builder
    protected BaseNodeProperties(UUID id, String name, String description,
            @NotNull NodeType type, boolean active) {
        this.id = id != null ? id : UUID.randomUUID();
        this.name = name != null ? name : generateDefaultName(type);
        this.description = description;
        this.type = type;
        this.active = active;
    }

    private String generateDefaultName(NodeType type) {
        return String.format("%s-%s",
                type.toString().toLowerCase(),
                UUID.randomUUID().toString().substring(0, 8));
    }

    public boolean isValid() {
        return id != null
                && name != null
                && type != null;
    }
}

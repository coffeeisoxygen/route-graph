package com.coffeecode.domain.entity;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.coffeecode.domain.objects.Coordinate;
import com.coffeecode.validation.ValidationUtils;

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
    private final Coordinate location;
    private final NodeType type;

    /**
     * Protected constructor to prevent instantiation from outside the package.
     *
     * @param location must not be null
     * @param type must not be null
     */
    protected NetworkNode(@NotNull final Coordinate location, @NotNull final NodeType type) {
        this.id = UUID.randomUUID();
        this.location = ValidationUtils.validate(location);
        this.type = ValidationUtils.validate(type);
    }

    public enum NodeType {
        SOURCE, CUSTOMER, JUNCTION
    }
}

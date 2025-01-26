package com.coffeecode.domain.entity;

import java.util.UUID;

import com.coffeecode.domain.objects.Coordinate;
import com.coffeecode.validation.ValidationUtils;

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
    protected NetworkNode(AbstractNodeBuilder<?> builder) {
        this.id = UUID.randomUUID();
        this.location = builder.location;
        this.type = builder.type;
    }

    public abstract static class AbstractNodeBuilder<T extends AbstractNodeBuilder<T>> {

        private Coordinate location;
        private NodeType type;

        protected abstract NetworkNode build();

        @SuppressWarnings("unchecked")
        protected final T self() {
            return (T) this;
        }

        public T location(Coordinate location) {
            this.location = location;
            return self();
        }

        public T type(NodeType type) {
            this.type = type;
            return self();
        }

        protected final NetworkNode validate(NetworkNode node) {
            return ValidationUtils.validate(node);
        }
    }
}

package com.coffeecode.domain.entity;

import java.util.UUID;

import com.coffeecode.domain.objects.Coordinate;

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
    protected NetworkNode(AbstractNodeBuilder<?> builder) {
        NodeValidation.validateLocation(builder.location);
        NodeValidation.validateType(builder.type);

        this.id = UUID.randomUUID();
        this.location = builder.location;
        this.type = builder.type;
    }

    public abstract static class AbstractNodeBuilder<T extends AbstractNodeBuilder<T>> {

        protected Coordinate location;
        protected NodeType type;

        protected abstract NetworkNode build();

        @SuppressWarnings("unchecked")
        protected final T self() {
            return (T) this;
        }

        public final T location(Coordinate location) {
            this.location = location;
            return self();
        }

        public final T type(NodeType type) {
            this.type = type;
            return self();
        }

        protected final NetworkNode validate(NetworkNode node) {
            return node; // Validation happens in constructor
        }
    }
}

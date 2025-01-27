package com.coffeecode.domain.entities;

import java.util.UUID;

import com.coffeecode.domain.values.location.Coordinate;
import com.coffeecode.validation.exceptions.ValidationException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Abstract base class representing a node in the water distribution network.
 * This class provides core functionality for all types of nodes including: -
 * Unique identification - Geographic location - Node type classification -
 * Immutable implementation - Builder pattern support
 *
 * @see WaterSource
 * @see WaterCustomer
 * @see NodeType
 */
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public abstract class NetworkNode {

    private final UUID id;
    private final Coordinate location;
    private final NodeType type;

    /**
     * Protected constructor for creating network nodes. Validates all required
     * parameters and ensures immutability.
     *
     * @param builder The builder containing node parameters
     * @throws ValidationException if any validation fails
     */
    protected NetworkNode(AbstractNodeBuilder<?> builder) {
        validateLocation(builder.location);
        validateType(builder.type);

        this.id = UUID.randomUUID();
        this.location = builder.location;
        this.type = builder.type;
    }

    private static void validateLocation(Coordinate location) {
        if (location == null) {
            throw new ValidationException("Location cannot be null");
        }
    }

    private static void validateType(NodeType type) {
        if (type == null) {
            throw new ValidationException("Node type cannot be null");
        }
        if (!isValidNodeType(type)) {
            throw new ValidationException("Invalid node type: " + type);
        }
    }

    private static boolean isValidNodeType(NodeType type) {
        return type == NodeType.CUSTOMER
                || type == NodeType.JUNCTION
                || type == NodeType.SOURCE;
    }

    /**
     * Abstract builder for network nodes with type-safe inheritance.
     *
     * @param <T> The concrete builder type
     */
    public abstract static class AbstractNodeBuilder<T extends AbstractNodeBuilder<T>> {

        protected Coordinate location;
        protected NodeType type;

        /**
         * Builds the concrete node instance.
         *
         * @return A new node instance
         * @throws ValidationException if validation fails
         */
        protected abstract NetworkNode build();

        /**
         * Type-safe self reference for builder inheritance.
         */
        @SuppressWarnings("unchecked")
        protected final T self() {
            return (T) this;
        }

        /**
         * Sets the node's geographic location.
         *
         * @param location The coordinate location
         * @return Builder instance for chaining
         */
        public final T location(Coordinate location) {
            this.location = location;
            return self();
        }

        /**
         * Sets the node type.
         *
         * @param type The node type
         * @return Builder instance for chaining
         */
        public final T type(NodeType type) {
            this.type = type;
            return self();
        }

        /**
         * Validates the built node instance.
         *
         * @param node The node to validate
         * @return The validated node
         */
        protected final NetworkNode validate(NetworkNode node) {
            return node; // Core validation happens in constructor
        }
    }
}

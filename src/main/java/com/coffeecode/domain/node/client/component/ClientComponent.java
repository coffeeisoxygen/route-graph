package com.coffeecode.domain.node.client.component;

/**
 * Base interface for all client components.
 * Defines the lifecycle and state management contract.
 */
public interface ClientComponent {
    void initialize();

    void clear();

    boolean isActive();
}

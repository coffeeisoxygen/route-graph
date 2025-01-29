package com.coffeecode.domain.node.router.component;

/**
 * Base interface for all router components.
 * Defines the lifecycle and state management contract.
 */
public interface RouterComponent {
    /**
     * Initialize the component.
     * Should be called before any other operations.
     */
    void initialize();

    /**
     * Clear all component data and reset state.
     * Used during shutdown or reset operations.
     */
    void clear();

    /**
     * Check if component is active and operational.
     *
     * @return true if component is active, false otherwise
     */
    boolean isActive();
}

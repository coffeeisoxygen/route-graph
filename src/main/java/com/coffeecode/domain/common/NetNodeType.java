package com.coffeecode.domain.common;

import lombok.Getter;

/**
 * Core network node types enum.
 * Represents the fundamental types of nodes in the network.
 */
@Getter
public enum NetNodeType {
    ROUTER("router"),
    CLIENT("client"),
    SERVER("server");

    private final String prefix;

    NetNodeType(String prefix) {
        this.prefix = prefix;
    }

    public String getNodePrefix() {
        return prefix;
    }
}

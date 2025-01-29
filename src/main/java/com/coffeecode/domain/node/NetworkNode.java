package com.coffeecode.domain.node;

import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.properties.NodeProperties;

public interface NetworkNode {
    NetworkIdentity getIdentity();

    NodeProperties getProperties();

    boolean isActive();

    void setActive(boolean state);

    boolean canAcceptConnection();

    boolean canInitiateConnection();
}

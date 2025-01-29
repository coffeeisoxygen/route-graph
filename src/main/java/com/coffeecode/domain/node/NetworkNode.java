package com.coffeecode.domain.node;

import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.properties.EdgeProperties;
import com.coffeecode.domain.properties.NodeProperties;

public interface NetworkNode {
    NetworkIdentity getIdentity();

    NodeProperties getProperties();

    boolean isActive();

    void setActive(boolean state);

    boolean canAcceptConnection();

    boolean canInitiateConnection();

    boolean connect(NetworkNode target, EdgeProperties props);
}

package com.coffeecode.domain.node.client;

import com.coffeecode.domain.edge.NetworkEdge;
import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.node.NetworkNode;
import com.coffeecode.domain.node.client.component.ClientComponents;
import com.coffeecode.domain.node.client.model.RequestType;
import com.coffeecode.domain.properties.EdgeProperties;
import com.coffeecode.domain.properties.NodeProperties;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClientNode implements NetworkNode {
    private final NetworkIdentity identity;
    private final NodeProperties properties;
    private final ClientComponents components;
    private volatile boolean active;

    @Builder(builderMethodName = "customBuilder")
    private ClientNode(NetworkIdentity identity, NodeProperties properties) {
        this.identity = identity;
        this.properties = properties;
        this.components = ClientComponents.create();
        initialize();
    }

    public static ClientNode create(NodeProperties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("Properties cannot be null");
        }
        return customBuilder()
                .identity(NetworkIdentity.create(properties.getType()))
                .properties(properties)
                .build();
    }

    private void initialize() {
        this.active = true;
        components.initialize();
    }

    @Override
    public boolean isActive() {
        return active && components.isActive();
    }

    @Override
    public void setActive(boolean state) {
        if (!state) {
            components.clear();
        } else {
            components.initialize();
        }
        this.active = state;
    }

    @Override
    public boolean canAcceptConnection() {
        return false; // Clients cannot accept incoming connections
    }

    @Override
    public boolean canInitiateConnection() {
        return isActive() &&
                components.getConnections().getConnectionCount() < properties.getMaxConnections();
    }

    public boolean connect(NetworkNode target, EdgeProperties props) {
        if (!canInitiateConnection() || !target.canAcceptConnection() || !props.isValid()) {
            return false;
        }

        NetworkEdge edge = NetworkEdge.builder()
                .source(this)
                .destination(target)
                .properties(props)
                .build();

        return components.getConnections().addConnection(edge);
    }

    public boolean queueRequest(NetworkIdentity target, RequestType type) {
        if (!isActive() || !isConnectedTo(target)) {
            return false;
        }
        return components.getRequests().queueRequest(target, type);
    }

    private boolean isConnectedTo(NetworkIdentity target) {
        return components.getConnections()
                .getConnections()
                .stream()
                .filter(NetworkEdge::isActive)
                .anyMatch(edge -> edge.getDestination()
                        .getIdentity()
                        .equals(target));
    }

    @Override
    public boolean removeRoute(NetworkIdentity destination) {
        return false; // Clients do not have routing tables
    }
}

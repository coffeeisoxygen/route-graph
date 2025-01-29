package com.coffeecode.domain.node.router.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.edge.NetworkEdge;
import com.coffeecode.domain.model.NodeType;
import com.coffeecode.domain.node.router.RouterNode;
import com.coffeecode.domain.properties.EdgeProperties;
import com.coffeecode.domain.properties.NodeProperties;

import lombok.Getter;

@ExtendWith(MockitoExtension.class)
@Getter
public abstract class RouterNodeTest {
    protected RouterNode routerNode;
    protected NodeProperties properties;
    protected static final long ROUTE_EXPIRY_MS = 1000;

    @BeforeEach
    void setUp() {
        properties = createDefaultProperties();
        routerNode = RouterNode.create(properties);
    }

    protected NodeProperties createDefaultProperties() {
        return NodeProperties.builder()
                .type(NodeType.ROUTER)
                .maxConnections(4)
                .bandwidth(100.0)
                .build();
    }

    protected NetworkEdge createValidEdge() {
        RouterNode destination = RouterNode.create(properties);
        EdgeProperties edgeProps = EdgeProperties.builder()
                .bandwidth(100.0)
                .latency(10.0)
                .build();

        return NetworkEdge.builder()
                .source(routerNode)
                .destination(destination)
                .properties(edgeProps)
                .active(true)
                .build();
    }
}

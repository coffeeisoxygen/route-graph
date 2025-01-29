package com.coffeecode.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.coffeecode.domain.factory.edge.EdgeFactory;
import com.coffeecode.domain.factory.node.NodeFactory;
import com.coffeecode.domain.node.properties.ClientNodeProperties;
import com.coffeecode.domain.node.properties.RouterNodeProperties;

@SpringJUnitConfig(classes = {NetworkConfig.class, TestConfig.class})
class NetworkConfigTest {
    @Autowired private NodeFactory nodeFactory;
    @Autowired private EdgeFactory edgeFactory;

    @Test
    void shouldCreateFactories() {
        assertThat(nodeFactory).isNotNull();
        assertThat(edgeFactory).isNotNull();
    }

    @Test
    void shouldCreateRouterWithProperties() {
        RouterNodeProperties props = RouterNodeProperties.builder()
                .routingCapacity(5)
                .bufferSize(1000.0)
                .build();

        var router = nodeFactory.createRouter(props);
        assertThat(router).isNotNull();
        assertThat(((RouterNodeProperties) router.getProperties()).getRoutingCapacity()).isEqualTo(5);
    }

    @Test
    void shouldCreateClientWithProperties() {
        ClientNodeProperties props = ClientNodeProperties.builder()
                .dataRate(100.0)
                .maxBandwidth(1000.0)
                .build();

        var client = nodeFactory.createClient(props);
        assertThat(client).isNotNull();
        assertThat(((ClientNodeProperties) client.getProperties()).getDataRate()).isEqualTo(100.0);
    }
}

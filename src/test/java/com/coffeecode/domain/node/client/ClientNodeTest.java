package com.coffeecode.domain.node.client;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.model.NodeType;
import com.coffeecode.domain.properties.NodeProperties;

@DisplayName("ClientNode Tests")
class ClientNodeTest {
    private ClientNode client;
    private NodeProperties properties;

    @BeforeEach
    void setUp() {
        properties = NodeProperties.builder()
            .type(NodeType.CLIENT)
            .maxConnections(1)
            .bandwidth(100.0)
            .build();
        client = ClientNode.create(properties);
    }

    @Nested
    @DisplayName("Basic Operations")
    class BasicOperationsTest {
        @Test
        @DisplayName("Should create with valid properties")
        void shouldCreateWithValidProperties() {
            assertThat(client.getProperties()).isEqualTo(properties);
            assertThat(client.isActive()).isTrue();
        }
    }
}

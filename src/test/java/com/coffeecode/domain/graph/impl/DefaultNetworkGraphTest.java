package com.coffeecode.domain.graph.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.model.NodeType;
import com.coffeecode.domain.node.NetworkNode;
import com.coffeecode.domain.node.router.RouterNode;
import com.coffeecode.domain.properties.EdgeProperties;
import com.coffeecode.domain.properties.NodeProperties;

@DisplayName("DefaultNetworkGraph Tests")
class DefaultNetworkGraphTest {
    private DefaultNetworkGraph graph;
    private NodeProperties properties;

    @BeforeEach
    void setUp() {
        graph = new DefaultNetworkGraph();
        properties = NodeProperties.builder()
                .type(NodeType.ROUTER)
                .maxConnections(4)
                .bandwidth(100.0)
                .build();
    }

    @Nested
    @DisplayName("Node Management")
    class NodeTests {
        @Test
        @DisplayName("Should add valid node")
        void shouldAddValidNode() {
            RouterNode node = RouterNode.create(properties);
            graph.addNode(node);
            assertThat(graph.getNodes()).containsKey(node.getIdentity());
        }

        @Test
        @DisplayName("Should reject null node")
        void shouldRejectNullNode() {
            assertThatThrownBy(() -> graph.addNode(null))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Path Finding")
    class PathTests {
        @Test
        @DisplayName("Should find direct path")
        void shouldFindDirectPath() {
            // Given
            RouterNode source = RouterNode.create(properties);
            RouterNode target = RouterNode.create(properties);

            graph.addNode(source);
            graph.addNode(target);

            EdgeProperties edgeProps = EdgeProperties.builder()
                    .bandwidth(100.0)
                    .latency(10.0)
                    .build();

            graph.connect(source, target, edgeProps);

            // When
            Optional<List<NetworkNode>> path = graph.findPath(source, target);

            // Then
            assertThat(path)
                    .isPresent()
                    .get()
                    .containsExactly(source, target);
        }
    }
}

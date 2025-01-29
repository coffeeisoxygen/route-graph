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
    @DisplayName("Connection Management")
    class ConnectionTests {
        @Test
        @DisplayName("Should connect valid nodes")
        void shouldConnectValidNodes() {
            // Given
            RouterNode source = RouterNode.create(properties);
            RouterNode target = RouterNode.create(properties);
            graph.addNode(source);
            graph.addNode(target);

            // When
            EdgeProperties edgeProps = EdgeProperties.builder()
                    .bandwidth(100.0)
                    .latency(10.0)
                    .build();
            graph.connect(source, target, edgeProps);

            // Then
            assertThat(graph.getAdjacencyList().get(source.getIdentity()))
                    .hasSize(1)
                    .first()
                    .matches(edge -> edge.getDestination().equals(target));
        }

        @Test
        @DisplayName("Should reject connection between non-existent nodes")
        void shouldRejectInvalidConnection() {
            // Given
            RouterNode source = RouterNode.create(properties);
            RouterNode target = RouterNode.create(properties);
            EdgeProperties edgeProps = EdgeProperties.builder()
                    .bandwidth(100.0)
                    .latency(10.0)
                    .build();

            // Then
            assertThatThrownBy(() -> graph.connect(source, target, edgeProps))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Both nodes must exist in the graph");
        }
    }

    @Nested
    @DisplayName("Path Finding")
    class PathFindingTests {
        @Test
        @DisplayName("Should find shortest path")
        void shouldFindShortestPath() {
            // Given
            RouterNode source = RouterNode.create(properties);
            RouterNode intermediate = RouterNode.create(properties);
            RouterNode target = RouterNode.create(properties);

            graph.addNode(source);
            graph.addNode(intermediate);
            graph.addNode(target);

            EdgeProperties props = EdgeProperties.builder()
                    .bandwidth(100.0)
                    .latency(10.0)
                    .build();

            graph.connect(source, intermediate, props);
            graph.connect(intermediate, target, props);

            // When
            Optional<List<NetworkNode>> path = graph.findPath(source, target);

            // Then
            assertThat(path).isPresent();
            List<NetworkNode> nodes = path.get();
            assertThat(nodes).hasSize(3);
            assertThat(nodes).satisfies(list -> {
                assertThat(list.get(0)).isEqualTo(source);
                assertThat(list.get(1)).isEqualTo(intermediate);
                assertThat(list.get(2)).isEqualTo(target);
            });
        }

        @Test
        @DisplayName("Should return empty when no path exists")
        void shouldReturnEmptyWhenNoPath() {
            // Given
            RouterNode source = RouterNode.create(properties);
            RouterNode target = RouterNode.create(properties);
            graph.addNode(source);
            graph.addNode(target);

            // When
            Optional<List<NetworkNode>> path = graph.findPath(source, target);

            // Then
            assertThat(path).isEmpty();
        }

        @Test
        @DisplayName("Should find direct path between nodes")
        void shouldFindDirectPath() {
            // Given
            RouterNode source = RouterNode.create(properties);
            RouterNode target = RouterNode.create(properties);
            graph.addNode(source);
            graph.addNode(target);

            EdgeProperties props = createEdgeProperties(100.0, 10.0);
            graph.connect(source, target, props);

            // When
            Optional<List<NetworkNode>> path = graph.findPath(source, target);

            // Then
            assertThat(path).isPresent();
            assertThat(path.get())
                    .hasSize(2)
                    .satisfies(nodes -> {
                        assertThat(nodes.get(0)).isEqualTo(source);
                        assertThat(nodes.get(1)).isEqualTo(target);
                    });
        }

        @Test
        @DisplayName("Should find path through intermediate nodes")
        void shouldFindIntermediatePath() {
            // Given
            RouterNode source = RouterNode.create(properties);
            RouterNode intermediate = RouterNode.create(properties);
            RouterNode target = RouterNode.create(properties);

            graph.addNode(source);
            graph.addNode(intermediate);
            graph.addNode(target);

            EdgeProperties props = createEdgeProperties(100.0, 10.0);
            graph.connect(source, intermediate, props);
            graph.connect(intermediate, target, props);

            // When
            Optional<List<NetworkNode>> path = graph.findPath(source, target);

            // Then
            assertThat(path).isPresent();
            assertThat(path.get())
                    .hasSize(3)
                    .satisfies(nodes -> {
                        assertThat(nodes.get(0)).isEqualTo(source);
                        assertThat(nodes.get(1)).isEqualTo(intermediate);
                        assertThat(nodes.get(2)).isEqualTo(target);
                    });
        }

        private EdgeProperties createEdgeProperties(double bandwidth, double latency) {
            return EdgeProperties.builder()
                    .bandwidth(bandwidth)
                    .latency(latency)
                    .build();
        }
    }
}

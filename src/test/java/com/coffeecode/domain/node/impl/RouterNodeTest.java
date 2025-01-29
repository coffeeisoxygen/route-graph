package com.coffeecode.domain.node.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.edge.NetworkEdge;
import com.coffeecode.domain.model.NodeType;
import com.coffeecode.domain.node.NetworkNode;
import com.coffeecode.domain.properties.EdgeProperties;
import com.coffeecode.domain.properties.NodeProperties;

@ExtendWith(MockitoExtension.class)
class RouterNodeTest {

    @Mock
    private NetworkEdge mockEdge;
    private RouterNode routerNode;
    private NodeProperties properties;

    @BeforeEach
    void setUp() {
        properties = NodeProperties.builder()
                .type(NodeType.ROUTER)
                .maxConnections(4)
                .bandwidth(100.0)
                .build();

        routerNode = RouterNode.create(properties);
    }

    @Nested
    @DisplayName("Connection Management Tests")
    class ConnectionTests {

        @Test
        @DisplayName("Should respect max connections limit")
        void shouldRespectMaxConnections() {
            // Given
            NetworkEdge edge = createValidEdge(routerNode);

            // When
            for (int i = 0; i < properties.getMaxConnections(); i++) {
                boolean added = routerNode.addConnection(edge);
                assertThat(added).isTrue();
            }

            // Then
            boolean exceeded = routerNode.addConnection(edge);
            assertThat(exceeded).isFalse();
            assertThat(routerNode.getCurrentConnections()).isEqualTo(properties.getMaxConnections());
        }

        @Test
        @DisplayName("Should handle concurrent connections safely")
        void shouldHandleConcurrentConnections() throws InterruptedException {
            // Given
            int threadCount = 10;
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            CountDownLatch latch = new CountDownLatch(threadCount);

            // When
            for (int i = 0; i < threadCount; i++) {
                executor.submit(() -> {
                    try {
                        routerNode.addConnection(createValidEdge(routerNode));
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();
            executor.shutdown();

            // Then
            assertThat(routerNode.getCurrentConnections())
                    .isLessThanOrEqualTo(properties.getMaxConnections());
        }
    }

    @Nested
    @DisplayName("Validation Tests")
    class ValidationTests {

        @Test
        @DisplayName("Should validate edges correctly")
        void shouldValidateEdges() {
            // Given
            NetworkEdge validEdge = createValidEdge(routerNode);
            NetworkEdge invalidEdge = null;

            // When & Then
            assertThat(routerNode.addConnection(validEdge)).isTrue();
            assertThat(routerNode.addConnection(invalidEdge)).isFalse();
        }

        @Test
        @DisplayName("Should clear connections when deactivated")
        void shouldClearConnectionsWhenDeactivated() {
            // Given
            routerNode.addConnection(createValidEdge(routerNode));

            // When
            routerNode.setActive(false);

            // Then
            assertThat(routerNode.getCurrentConnections()).isZero();
            assertThat(routerNode.isActive()).isFalse();
        }
    }

    private NetworkEdge createValidEdge(RouterNode source) {
        NetworkNode destination = RouterNode.create(properties);
        EdgeProperties edgeProps = new EdgeProperties(100.0, 10.0);

        return NetworkEdge.builder()
                .source(source)
                .destination(destination)
                .properties(edgeProps)
                .build();
    }
}

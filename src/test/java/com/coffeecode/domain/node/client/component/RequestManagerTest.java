package com.coffeecode.domain.node.client.component;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.model.NodeType;
import com.coffeecode.domain.node.client.model.RequestType;

@DisplayName("RequestManager Tests")
class RequestManagerTest {
    private RequestManager manager;
    private NetworkIdentity targetNode;

    @BeforeEach
    void setUp() {
        manager = new RequestManager();
        manager.initialize();
        targetNode = NetworkIdentity.create(NodeType.SERVER);
    }

    @Nested
    @DisplayName("Lifecycle Management")
    class LifecycleTests {
        @Test
        @DisplayName("Should be active after initialization")
        void shouldBeActiveAfterInit() {
            assertThat(manager.isActive()).isTrue();
        }

        @Test
        @DisplayName("Should clear state properly")
        void shouldClearState() {
            manager.queueRequest(targetNode, RequestType.CONNECT);
            manager.clear();

            assertThat(manager.isActive()).isFalse();
            assertThat(manager.getPendingRequests()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Request Queue Management")
    class QueueTests {
        @Test
        @DisplayName("Should queue valid request")
        void shouldQueueValidRequest() {
            boolean result = manager.queueRequest(targetNode, RequestType.CONNECT);

            assertThat(result).isTrue();
            assertThat(manager.getPendingRequests())
                    .hasSize(1)
                    .first()
                    .satisfies(request -> {
                        assertThat(request.getTarget()).isEqualTo(targetNode);
                        assertThat(request.getType()).isEqualTo(RequestType.CONNECT);
                    });
        }

        @Test
        @DisplayName("Should reject request when inactive")
        void shouldRejectWhenInactive() {
            manager.clear();
            boolean result = manager.queueRequest(targetNode, RequestType.CONNECT);

            assertThat(result).isFalse();
            assertThat(manager.getPendingRequests()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Metrics Collection")
    class MetricsTests {
        @Test
        @DisplayName("Should track successful requests")
        void shouldTrackSuccessfulRequests() {
            // Given
            assertThat(manager.isActive()).isTrue();

            // When
            boolean added = manager.queueRequest(targetNode, RequestType.CONNECT);

            // Then
            assertThat(added).isTrue();
            assertThat(manager.getPendingRequests()).hasSize(1);
            assertThat(manager.getMetrics().getRequestMetrics().getTotalRequests().get()).isEqualTo(1);
            assertThat(manager.getMetrics().getRequestMetrics().getSuccessfulRequests().get()).isEqualTo(1);
        }

        @Test
        @DisplayName("Should track failed requests")
        void shouldTrackFailedRequests() {
            // Given
            manager.clear();
            assertThat(manager.isActive()).isFalse();

            // When
            boolean added = manager.queueRequest(targetNode, RequestType.CONNECT);

            // Then
            assertThat(added).isFalse();
            assertThat(manager.getPendingRequests()).isEmpty();
            assertThat(manager.getMetrics().getRequestMetrics().getFailedRequests().get()).isEqualTo(1);
        }
    }
}

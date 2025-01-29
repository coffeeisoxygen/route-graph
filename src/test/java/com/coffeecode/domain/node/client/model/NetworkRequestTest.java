package com.coffeecode.domain.node.client.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.model.NodeType;

@DisplayName("NetworkRequest Tests")
class NetworkRequestTest {
    private NetworkIdentity target;

    @BeforeEach
    void setUp() {
        target = NetworkIdentity.create(NodeType.SERVER);
    }

    @Nested
    @DisplayName("Request Creation")
    class CreationTests {
        @Test
        @DisplayName("Should create valid request")
        void shouldCreateValidRequest() {
            NetworkRequest request = NetworkRequest.builder()
                .target(target)
                .type(RequestType.CONNECT)
                .build();

            assertThat(request)
                .satisfies(r -> {
                    assertThat(r.getTarget()).isEqualTo(target);
                    assertThat(r.getType()).isEqualTo(RequestType.CONNECT);
                    assertThat(r.getStatus()).isEqualTo(RequestStatus.PENDING);
                    assertThat(r.getTimestamp()).isPositive();
                });
        }

        @Test
        @DisplayName("Should validate request properly")
        void shouldValidateRequest() {
            NetworkRequest validRequest = NetworkRequest.builder()
                .target(target)
                .type(RequestType.CONNECT)
                .build();

            NetworkRequest invalidRequest = NetworkRequest.builder()
                .target(null)
                .type(RequestType.CONNECT)
                .build();

            assertThat(validRequest.isValid()).isTrue();
            assertThat(invalidRequest.isValid()).isFalse();
        }
    }

    @Nested
    @DisplayName("Request Properties")
    class PropertyTests {
        @Test
        @DisplayName("Should have default timestamp")
        void shouldHaveDefaultTimestamp() {
            long before = System.currentTimeMillis();

            NetworkRequest request = NetworkRequest.builder()
                .target(target)
                .type(RequestType.CONNECT)
                .build();

            long after = System.currentTimeMillis();

            assertThat(request.getTimestamp())
                .isBetween(before, after);
        }

        @Test
        @DisplayName("Should have default pending status")
        void shouldHaveDefaultStatus() {
            NetworkRequest request = NetworkRequest.builder()
                .target(target)
                .type(RequestType.CONNECT)
                .build();

            assertThat(request.getStatus())
                .isEqualTo(RequestStatus.PENDING);
        }
    }
}

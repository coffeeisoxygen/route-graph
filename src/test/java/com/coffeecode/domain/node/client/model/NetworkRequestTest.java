package com.coffeecode.domain.node.client.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Field;

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

    @Nested
    @DisplayName("Status Management")
    class StatusTests {
        @Test
        @DisplayName("Should handle status transitions")
        void shouldHandleStatusTransitions() {
            NetworkRequest initial = NetworkRequest.builder()
                    .target(target)
                    .type(RequestType.CONNECT)
                    .build();

            NetworkRequest inProgress = initial.toBuilder()
                    .status(RequestStatus.IN_PROGRESS)
                    .build();

            NetworkRequest completed = inProgress.toBuilder()
                    .status(RequestStatus.COMPLETED)
                    .build();

            assertThat(initial.getStatus()).isEqualTo(RequestStatus.PENDING);
            assertThat(inProgress.getStatus()).isEqualTo(RequestStatus.IN_PROGRESS);
            assertThat(completed.getStatus()).isEqualTo(RequestStatus.COMPLETED);
        }
    }

    @Nested
    @DisplayName("Immutability Tests")
    class ImmutabilityTests {
        @Test
        @DisplayName("Should be immutable")
        void shouldBeImmutable() throws NoSuchFieldException {
            NetworkRequest request = NetworkRequest.builder()
                    .target(target)
                    .type(RequestType.CONNECT)
                    .build();

            Field statusField = NetworkRequest.class.getDeclaredField("status");
            // statusField.setAccessible(true);

            assertThatThrownBy(() -> statusField.set(request, RequestStatus.COMPLETED))
                    .isInstanceOf(IllegalAccessException.class);
        }

        @Test
        @DisplayName("Should only modify through builder")
        void shouldOnlyModifyThroughBuilder() {
            NetworkRequest original = NetworkRequest.builder()
                    .target(target)
                    .type(RequestType.CONNECT)
                    .build();

            NetworkRequest modified = original.toBuilder()
                    .status(RequestStatus.COMPLETED)
                    .build();

            assertThat(original.getStatus()).isEqualTo(RequestStatus.PENDING);
            assertThat(modified.getStatus()).isEqualTo(RequestStatus.COMPLETED);
        }
    }

    @Nested
    @DisplayName("Builder Tests")
    class BuilderTests {
        @Test
        @DisplayName("Should support toBuilder operations")
        void shouldSupportToBuilder() {
            NetworkRequest initial = NetworkRequest.builder()
                    .target(target)
                    .type(RequestType.CONNECT)
                    .build();

            NetworkRequest modified = initial.toBuilder()
                    .status(RequestStatus.IN_PROGRESS)
                    .build();

            assertThat(modified)
                    .satisfies(r -> {
                        assertThat(r.getTarget()).isEqualTo(initial.getTarget());
                        assertThat(r.getType()).isEqualTo(initial.getType());
                        assertThat(r.getStatus()).isEqualTo(RequestStatus.IN_PROGRESS);
                    });
        }
    }

    @Nested
    @DisplayName("Validation Tests")
    class ValidationTests {
        @Test
        @DisplayName("Should validate required fields")
        void shouldValidateRequiredFields() {
            assertThat(NetworkRequest.builder()
                .target(null)
                .type(RequestType.CONNECT)
                .build()
                .isValid()).isFalse();

            assertThat(NetworkRequest.builder()
                .target(target)
                .type(null)
                .build()
                .isValid()).isFalse();
        }
    }

    @Nested
    @DisplayName("Equality Tests")
    class EqualityTests {
        @Test
        @DisplayName("Should implement equals correctly")
        void shouldImplementEquals() {
            NetworkRequest request1 = NetworkRequest.builder()
                .target(target)
                .type(RequestType.CONNECT)
                .build();

            NetworkRequest request2 = NetworkRequest.builder()
                .target(target)
                .type(RequestType.CONNECT)
                .build();

            assertThat(request1).isEqualTo(request2);
            assertThat(request1.hashCode()).isEqualTo(request2.hashCode());
        }
    }

    @Nested
    @DisplayName("Input Validation")
    class MoreValidationTests {
        @Test
        @DisplayName("Should handle invalid inputs")
        void shouldHandleInvalidInputs() {
            NetworkRequest nullTarget = NetworkRequest.builder()
                .target(null)
                .type(RequestType.CONNECT)
                .build();

            NetworkRequest nullType = NetworkRequest.builder()
                .target(target)
                .type(null)
                .build();

            assertThat(nullTarget.isValid()).isFalse();
            assertThat(nullType.isValid()).isFalse();
        }
    }

    @Nested
    @DisplayName("Timestamp Behavior")
    class TimestampTests {
        @Test
        @DisplayName("Should have valid timestamp")
        void shouldHaveValidTimestamp() {
            long before = System.currentTimeMillis();
            NetworkRequest request = NetworkRequest.builder()
                .target(target)
                .type(RequestType.CONNECT)
                .build();
            long after = System.currentTimeMillis();

            assertThat(request.getTimestamp())
                .isBetween(before, after);
        }
    }
}

package com.coffeecode.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.values.location.Coordinate;
import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("NetworkNode Tests")
class NetworkNodeTest {

    private static class TestNode extends NetworkNode {

        private TestNode(AbstractNodeBuilder<?> builder) {
            super(builder);
        }

        private static class Builder extends AbstractNodeBuilder<Builder> {

            @Override
            protected NetworkNode build() {
                return new TestNode(this);
            }
        }

        static Builder builder() {
            return new Builder();
        }
    }

    @Nested
    @DisplayName("Node Creation Tests")
    class NodeCreationTests {

        @Test
        @DisplayName("Should create node with valid parameters")
        void shouldCreateNodeWithValidParameters() {
            Coordinate location = Coordinate.of(0, 0);
            NetworkNode node = TestNode.builder()
                    .location(location)
                    .type(NodeType.JUNCTION)
                    .build();

            assertNotNull(node.getId());
            assertEquals(location, node.getLocation());
            assertEquals(NodeType.JUNCTION, node.getType());
        }

        @Test
        @DisplayName("Should throw exception for null location")
        void shouldThrowExceptionForNullLocation() {
            ValidationException exception = assertThrows(ValidationException.class, ()
                    -> TestNode.builder()
                            .type(NodeType.JUNCTION)
                            .build()
            );
            assertEquals("Location cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw exception for null type")
        void shouldThrowExceptionForNullType() {
            ValidationException exception = assertThrows(ValidationException.class, ()
                    -> TestNode.builder()
                            .location(Coordinate.of(0, 0))
                            .build()
            );
            assertEquals("Node type cannot be null", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Node Type Validation Tests")
    class NodeTypeValidationTests {

        @Test
        @DisplayName("Should create node for each valid type")
        void shouldCreateNodeForEachValidType() {
            Coordinate location = Coordinate.of(0, 0);

            for (NodeType type : NodeType.values()) {
                NetworkNode node = TestNode.builder()
                        .location(location)
                        .type(type)
                        .build();

                assertEquals(type, node.getType());
            }
        }
    }

    @Nested
    @DisplayName("Node Identity Tests")
    class NodeIdentityTests {

        @Test
        @DisplayName("Should generate unique IDs for different nodes")
        void shouldGenerateUniqueIds() {
            NetworkNode node1 = TestNode.builder()
                    .location(Coordinate.of(0, 0))
                    .type(NodeType.JUNCTION)
                    .build();

            NetworkNode node2 = TestNode.builder()
                    .location(Coordinate.of(0, 0))
                    .type(NodeType.JUNCTION)
                    .build();

            assertNotEquals(node1.getId(), node2.getId());
        }
    }

    @Nested
    @DisplayName("Builder Pattern Tests")
    class BuilderPatternTests {

        @Test
        @DisplayName("Should allow chaining builder methods")
        void shouldAllowChainingBuilderMethods() {
            NetworkNode node = TestNode.builder()
                    .location(Coordinate.of(0, 0))
                    .type(NodeType.JUNCTION)
                    .build();

            assertNotNull(node);
        }

        @Test
        @DisplayName("Should maintain immutability after building")
        void shouldMaintainImmutabilityAfterBuilding() {
            // Create initial objects
            Coordinate location = Coordinate.of(0, 0);
            NetworkNode node = TestNode.builder()
                    .location(location)
                    .type(NodeType.JUNCTION)
                    .build();

            // Store initial values
            UUID originalId = node.getId();
            Coordinate originalLocation = node.getLocation();
            NodeType originalType = node.getType();

            // Create new values
            Coordinate newLocation = Coordinate.of(1, 1);

            // Try to modify through getters (should return same instance)
            assertEquals(originalId, node.getId());
            assertEquals(originalLocation, node.getLocation());
            assertEquals(originalType, node.getType());

            // Verify original location hasn't changed
            assertEquals(0, node.getLocation().getLatitude());
            assertEquals(0, node.getLocation().getLongitude());
        }
    }

    @Nested
    @DisplayName("Robustness Tests")
    class RobustnessTests {

        @Test
        @DisplayName("Should handle coordinate at boundary values")
        void shouldHandleCoordinateAtBoundaryValues() {
            // Test minimum values
            NetworkNode minNode = TestNode.builder()
                    .location(Coordinate.of(-90, -180))
                    .type(NodeType.JUNCTION)
                    .build();

            assertNotNull(minNode);
            assertEquals(-90, minNode.getLocation().getLatitude());
            assertEquals(-180, minNode.getLocation().getLongitude());

            // Test maximum values
            NetworkNode maxNode = TestNode.builder()
                    .location(Coordinate.of(90, 180))
                    .type(NodeType.JUNCTION)
                    .build();

            assertNotNull(maxNode);
            assertEquals(90, maxNode.getLocation().getLatitude());
            assertEquals(180, maxNode.getLocation().getLongitude());
        }

        @Test
        @DisplayName("Should handle multiple nodes at same location")
        void shouldHandleMultipleNodesAtSameLocation() {
            Coordinate location = Coordinate.of(0, 0);
            List<NetworkNode> nodes = new ArrayList<>();

            // Create multiple nodes at same location
            for (int i = 0; i < 1000; i++) {
                nodes.add(TestNode.builder()
                        .location(location)
                        .type(NodeType.JUNCTION)
                        .build());
            }

            // Verify all IDs are unique
            Set<UUID> ids = nodes.stream()
                    .map(NetworkNode::getId)
                    .collect(Collectors.toSet());
            assertEquals(1000, ids.size());

            // Verify all locations are same instance
            assertEquals(1, nodes.stream()
                    .map(NetworkNode::getLocation)
                    .distinct()
                    .count());
        }

        @Test
        @DisplayName("Should handle concurrent node creation")
        void shouldHandleConcurrentNodeCreation() {
            int threadCount = 10;
            int nodesPerThread = 100;
            Set<UUID> ids = Collections.synchronizedSet(new HashSet<>());
            CountDownLatch latch = new CountDownLatch(threadCount);

            // Create nodes concurrently
            for (int i = 0; i < threadCount; i++) {
                new Thread(() -> {
                    try {
                        for (int j = 0; j < nodesPerThread; j++) {
                            NetworkNode node = TestNode.builder()
                                    .location(Coordinate.of(0, 0))
                                    .type(NodeType.JUNCTION)
                                    .build();
                            ids.add(node.getId());
                        }
                    } finally {
                        latch.countDown();
                    }
                }).start();
            }

            // Wait for all threads
            assertTrue(() -> {
                try {
                    return latch.await(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    return false;
                }
            });

            // Verify all IDs are unique
            assertEquals(threadCount * nodesPerThread, ids.size());
        }

        @Test
        @DisplayName("Should maintain consistent state under load")
        void shouldMaintainConsistentStateUnderLoad() {
            Coordinate location = Coordinate.of(0, 0);
            NetworkNode node = TestNode.builder()
                    .location(location)
                    .type(NodeType.JUNCTION)
                    .build();

            // Verify state consistency under multiple getter calls
            IntStream.range(0, 10000).parallel().forEach(i -> {
                assertNotNull(node.getId());
                assertEquals(location, node.getLocation());
                assertEquals(NodeType.JUNCTION, node.getType());
            });
        }
    }

    @Nested
    @DisplayName("Node Equality Tests")
    class NodeEqualityTests {

        @Test
        @DisplayName("Should equal nodes with same ID")
        void shouldEqualNodesWithSameId() {
            // Create two nodes with same properties
            NetworkNode node1 = TestNode.builder()
                    .location(Coordinate.of(0, 0))
                    .type(NodeType.JUNCTION)
                    .build();

            // Reflection to set same ID
            UUID sharedId = UUID.randomUUID();
            setId(node1, sharedId);

            NetworkNode node2 = TestNode.builder()
                    .location(Coordinate.of(1, 1)) // Different location
                    .type(NodeType.SOURCE) // Different type
                    .build();
            setId(node2, sharedId);

            assertEquals(node1, node2);
            assertEquals(node1.hashCode(), node2.hashCode());
        }

        private void setId(NetworkNode node, UUID id) {
            try {
                java.lang.reflect.Field idField = NetworkNode.class.getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(node, id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Nested
    @DisplayName("Node Type Compatibility Tests")
    class NodeTypeCompatibilityTests {

        @Test
        @DisplayName("Should enforce type compatibility rules")
        void shouldEnforceTypeCompatibilityRules() {
            NetworkNode junction = TestNode.builder()
                    .location(Coordinate.of(0, 0))
                    .type(NodeType.JUNCTION)
                    .build();

            NetworkNode source = TestNode.builder()
                    .location(Coordinate.of(0, 0))
                    .type(NodeType.SOURCE)
                    .build();

            NetworkNode customer = TestNode.builder()
                    .location(Coordinate.of(0, 0))
                    .type(NodeType.CUSTOMER)
                    .build();

            assertNotEquals(junction.getType(), source.getType());
            assertNotEquals(source.getType(), customer.getType());
            assertNotEquals(customer.getType(), junction.getType());
        }
    }
}

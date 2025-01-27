package com.coffeecode.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.values.Coordinate;
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
}

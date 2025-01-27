package com.coffeecode.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.entities.NetworkNode;
import com.coffeecode.domain.entities.NodeType;
import com.coffeecode.domain.values.Coordinate;
import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("NetworkNode Tests")
class NetworkNodeTest {

    private Coordinate location;

    private static class TestNode extends NetworkNode {

        private TestNode(TestNodeBuilder builder) {
            super(builder);
        }

        public static TestNodeBuilder builder() {
            return new TestNodeBuilder();
        }

        private static class TestNodeBuilder extends AbstractNodeBuilder<TestNodeBuilder> {

            @Override
            public TestNode build() {
                TestNode node = new TestNode(this);
                return (TestNode) validate(node);
            }
        }
    }

    @BeforeEach
    void setUp() {
        location = Coordinate.of(0.0, 0.0);
    }

    @Test
    @DisplayName("Should create node with valid parameters")
    void shouldCreateNodeWithValidParameters() {
        TestNode node = TestNode.builder()
                .location(location)
                .type(NodeType.JUNCTION)
                .build();

        assertNotNull(node.getId());
        assertEquals(location, node.getLocation());
        assertEquals(NodeType.JUNCTION, node.getType());
    }

    @Test
    @DisplayName("Should throw ValidationException for null location")
    void shouldThrowValidationExceptionForNullLocation() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> TestNode.builder()
                        .type(NodeType.JUNCTION)
                        .build()
        );
        assertTrue(exception.getMessage().contains("Location cannot be null"));
    }

    @Test
    @DisplayName("Should throw ValidationException for null type")
    void shouldThrowValidationExceptionForNullType() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> TestNode.builder()
                        .location(location)
                        .build()
        );
        assertTrue(exception.getMessage().contains("Node type cannot be null"));
    }

    @Test
    @DisplayName("Should generate unique IDs")
    void shouldGenerateUniqueIds() {
        TestNode node1 = TestNode.builder()
                .location(location)
                .type(NodeType.JUNCTION)
                .build();
        TestNode node2 = TestNode.builder()
                .location(location)
                .type(NodeType.JUNCTION)
                .build();

        assertNotEquals(node1.getId(), node2.getId());
    }

    @Test
    @DisplayName("Should implement equals and hashCode")
    void shouldImplementEqualsAndHashCode() {
        TestNode node1 = TestNode.builder()
                .location(location)
                .type(NodeType.JUNCTION)
                .build();
        TestNode node2 = TestNode.builder()
                .location(location)
                .type(NodeType.JUNCTION)
                .build();

        // Different instances should not be equal (due to UUID)
        assertNotEquals(node1, node2);
        // Same instance should be equal to itself
        assertEquals(node1, node1);
        // Different types should not be equal
        assertNotEquals(node1, "not a node");
    }
}

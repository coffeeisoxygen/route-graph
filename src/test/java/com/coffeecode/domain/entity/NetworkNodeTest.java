package com.coffeecode.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.entity.NetworkNode.NodeType;
import com.coffeecode.domain.objects.Coordinate;
import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("NetworkNode Tests")
class NetworkNodeTest {

    private Coordinate location;

    // Test implementation class
    private static class TestNode extends NetworkNode {
        TestNode(Coordinate location, NodeType type) {
            super(location, type);
        }
    }

    @BeforeEach
    void setUp() {
        location = Coordinate.of(0.0, 0.0);
    }

    @Test
    @DisplayName("Should create node with valid parameters")
    void shouldCreateNodeWithValidParameters() {
        TestNode node = new TestNode(location, NodeType.JUNCTION);

        assertNotNull(node.getId());
        assertEquals(location, node.getLocation());
        assertEquals(NodeType.JUNCTION, node.getType());
    }

    @Test
    @DisplayName("Should throw ValidationException for null location")
    void shouldThrowValidationExceptionForNullLocation() {
        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> new TestNode(null, NodeType.JUNCTION)
        );
        assertTrue(exception.getMessage().contains("Location cannot be null"));
    }

    @Test
    @DisplayName("Should throw ValidationException for null type")
    void shouldThrowValidationExceptionForNullType() {
        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> new TestNode(location, null)
        );
        assertTrue(exception.getMessage().contains("Node type cannot be null"));
    }

    @Test
    @DisplayName("Should generate unique IDs")
    void shouldGenerateUniqueIds() {
        TestNode node1 = new TestNode(location, NodeType.JUNCTION);
        TestNode node2 = new TestNode(location, NodeType.JUNCTION);

        assertNotEquals(node1.getId(), node2.getId());
    }

    @Test
    @DisplayName("Should implement equals and hashCode correctly")
    void shouldImplementEqualsAndHashCode() {
        TestNode node1 = new TestNode(location, NodeType.JUNCTION);
        TestNode node2 = new TestNode(location, NodeType.JUNCTION);

        // Different objects with same data should not be equal (due to UUID)
        assertNotEquals(node1, node2);
        // Same object should be equal to itself
        assertEquals(node1, node1);
    }
}

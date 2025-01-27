package com.coffeecode.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}

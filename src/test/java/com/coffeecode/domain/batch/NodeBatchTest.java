package com.coffeecode.domain.batch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.model.NodeType;
import com.coffeecode.domain.node.router.RouterNode;
import com.coffeecode.domain.properties.NodeProperties;

@DisplayName("NodeBatch Tests")
class NodeBatchTest {

    @Nested
    @DisplayName("Batch Creation Tests")
    class BatchCreationTests {

        @Test
        @DisplayName("Should create batch with default size")
        void shouldCreateDefaultBatch() {
            // Given
            NodeBatch batch = NodeBatch.builder().build();

            // When
            List<RouterNode> nodes = batch.createRouters();

            // Then
            assertThat(nodes).hasSize(1);
            assertThat(nodes.get(0).getProperties())
                    .isEqualTo(batch.getDefaultProperties());
        }

        @Test
        @DisplayName("Should create batch with custom size")
        void shouldCreateCustomBatch() {
            // Given
            int batchSize = 5;
            NodeBatch batch = NodeBatch.builder()
                    .batchSize(batchSize)
                    .build();

            // When
            List<RouterNode> nodes = batch.createRouters();

            // Then
            assertThat(nodes).hasSize(batchSize);
        }
    }

    @Nested
    @DisplayName("Validation Tests")
    class ValidationTests {

        @Test
        @DisplayName("Should throw on invalid batch size")
        void shouldThrowOnInvalidBatchSize() {
            // Given
            NodeBatch batch = NodeBatch.builder()
                    .batchSize(-1)
                    .build();

            // When/Then
            assertThatThrownBy(() -> batch.createRouters())
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Batch size must be positive");
        }
    }

    @Nested
    @DisplayName("Property Tests")
    class PropertyTests {

        @Test
        @DisplayName("Should create batch with custom properties")
        void shouldCreateWithCustomProperties() {
            // Given
            NodeProperties customProps = NodeProperties.builder()
                    .maxConnections(10)
                    .bandwidth(200.0)
                    .type(NodeType.ROUTER)
                    .build();

            NodeBatch batch = NodeBatch.builder()
                    .batchSize(3)
                    .defaultProperties(customProps)
                    .build();

            // When
            List<RouterNode> nodes = batch.createRouters();

            // Then
            assertThat(nodes)
                    .hasSize(3)
                    .allMatch(node -> node.getProperties().equals(customProps));
        }
    }
}

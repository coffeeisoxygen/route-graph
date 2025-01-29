package com.coffeecode.domain.batch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.coffeecode.domain.model.NodeType;
import com.coffeecode.domain.properties.NodeProperties;

class NodeBatchTest {

    @Test
    void defaultBatch_ShouldCreateSingleRouter() {
        NodeBatch batch = NodeBatch.builder().build();
        var routers = batch.createRouters();

        assertThat(routers)
                .hasSize(1)
                .allSatisfy(router -> {
                    assertThat(router.isActive()).isTrue();
                    assertThat(router.getProperties().getType()).isEqualTo(NodeType.ROUTER);
                });
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 5, 10 })
    void customBatch_ShouldCreateSpecifiedNumberOfRouters(int batchSize) {
        NodeBatch batch = NodeBatch.builder()
                .batchSize(batchSize)
                .defaultProperties(NodeProperties.builder()
                        .type(NodeType.ROUTER)
                        .maxConnections(4)
                        .bandwidth(100.0)
                        .build())
                .build();

        var routers = batch.createRouters();
        assertThat(routers).hasSize(batchSize);
    }

    @Test
    void invalidBatchSize_ShouldThrowException() {
        NodeBatch batch = NodeBatch.builder()
                .batchSize(-1)
                .build();

        assertThatThrownBy(() -> batch.createRouters())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Batch size must be positive");
    }
}

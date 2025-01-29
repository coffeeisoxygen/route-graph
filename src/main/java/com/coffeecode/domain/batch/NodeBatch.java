package com.coffeecode.domain.batch;

import java.util.ArrayList;
import java.util.List;

import com.coffeecode.domain.model.NodeType;
import com.coffeecode.domain.node.router.RouterNode;
import com.coffeecode.domain.properties.NodeProperties;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NodeBatch {
    @Builder.Default
    int batchSize = 1;

    @Builder.Default
    NodeProperties defaultProperties = NodeProperties.builder()
        .maxConnections(4)
        .bandwidth(100.0)
        .type(NodeType.ROUTER)
        .build();

    public List<RouterNode> createRouters() {
        validateBatchSize();
        return generateRouters();
    }

    private List<RouterNode> generateRouters() {
        List<RouterNode> nodes = new ArrayList<>(batchSize);
        for (int i = 0; i < batchSize; i++) {
            nodes.add(RouterNode.create(defaultProperties));
        }
        return List.copyOf(nodes); // Return immutable list
    }

    private void validateBatchSize() {
        if (batchSize < 1) {
            throw new IllegalArgumentException("Batch size must be positive");
        }
    }
}

package com.coffeecode.domain.common;

import com.coffeecode.domain.edge.Edge;
import com.coffeecode.domain.edge.properties.EdgeProperties;
import com.coffeecode.domain.node.base.Node;

public class TestUtils {
    public static final String TEST_PREFIX = "test";
    public static final double DEFAULT_BANDWIDTH = 100.0;
    public static final double DEFAULT_BUFFER = 1000.0;

    public static Edge createTestEdge(Node source, Node target) {
        return Edge.builder()
                .source(source)
                .destination(target)
                .properties(EdgeProperties.builder()
                        .bandwidth(DEFAULT_BANDWIDTH)
                        .latency(10.0)
                        .build())
                .active(true)
                .build();
    }
}

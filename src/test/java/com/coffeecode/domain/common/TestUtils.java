package com.coffeecode.domain.common;

import com.coffeecode.domain.edge.NetEdge;
import com.coffeecode.domain.edge.properties.NetEdgeProperties;
import com.coffeecode.domain.node.base.NetNode;

public class TestUtils {
    public static final String TEST_PREFIX = "test";
    public static final double DEFAULT_BANDWIDTH = 100.0;
    public static final double DEFAULT_BUFFER = 1000.0;

    public static NetEdge createTestEdge(NetNode source, NetNode target) {
        return NetEdge.builder()
                .source(source)
                .destination(target)
                .properties(NetEdgeProperties.builder()
                        .bandwidth(DEFAULT_BANDWIDTH)
                        .latency(10.0)
                        .build())
                .active(true)
                .build();
    }
}

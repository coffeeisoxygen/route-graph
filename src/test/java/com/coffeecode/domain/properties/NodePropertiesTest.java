package com.coffeecode.domain.properties;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.coffeecode.domain.model.NodeType;

public class NodePropertiesTest {

    @Test
    public void testIsValid_withValidProperties_shouldReturnTrue() {
        NodeProperties nodeProperties = NodeProperties.builder()
                .maxConnections(5)
                .bandwidth(200.0)
                .type(NodeType.ROUTER)
                .build();

        assertThat(nodeProperties.isValid()).isTrue();
    }

    @Test
    public void testIsValid_withNegativeMaxConnections_shouldReturnFalse() {
        NodeProperties nodeProperties = NodeProperties.builder()
                .maxConnections(-1)
                .bandwidth(200.0)
                .type(NodeType.ROUTER)
                .build();

        assertThat(nodeProperties.isValid()).isFalse();
    }

    @Test
    public void testIsValid_withNegativeBandwidth_shouldReturnFalse() {
        NodeProperties nodeProperties = NodeProperties.builder()
                .maxConnections(5)
                .bandwidth(-100.0)
                .type(NodeType.ROUTER)
                .build();

        assertThat(nodeProperties.isValid()).isFalse();
    }

    @Test
    public void testIsValid_withNullType_shouldReturnFalse() {
        NodeProperties nodeProperties = NodeProperties.builder()
                .maxConnections(5)
                .bandwidth(200.0)
                .type(null)
                .build();

        assertThat(nodeProperties.isValid()).isFalse();
    }
}

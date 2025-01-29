package com.coffeecode.domain.edge.properties;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.coffeecode.domain.entities.edge.EdgeProperties;

class EdgePropertiesTest {

    @Test
    void shouldValidateValidProperties() {
        EdgeProperties props = EdgeProperties.builder()
                .bandwidth(100.0)
                .latency(5.0)
                .jitter(1.0)
                .packetLoss(0.1)
                .mtu(1500)
                .cost(10.0)
                .build();

        assertTrue(props.isValid());
    }

    @Test
    void shouldInvalidateNegativeBandwidth() {
        EdgeProperties props = EdgeProperties.builder()
                .bandwidth(-100.0)
                .latency(5.0)
                .build();

        assertFalse(props.isValid());
    }

    @Test
    void shouldInvalidateNegativeLatency() {
        EdgeProperties props = EdgeProperties.builder()
                .bandwidth(100.0)
                .latency(-5.0)
                .build();

        assertFalse(props.isValid());
    }

    @Test
    void shouldInvalidateInvalidPacketLoss() {
        EdgeProperties props = EdgeProperties.builder()
                .bandwidth(100.0)
                .latency(5.0)
                .packetLoss(101.0)
                .build();

        assertFalse(props.isValid());
    }

    @Test
    void shouldValidateMinimalProperties() {
        EdgeProperties props = EdgeProperties.builder()
                .bandwidth(100.0)
                .latency(5.0)
                .build();

        assertTrue(props.isValid());
    }
}

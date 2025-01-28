package com.coffeecode.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NetworkMonitorTest {
    private NetworkMonitor monitor;

    @BeforeEach
    void setUp() {
        monitor = new NetworkMonitor();
    }

    @Test
    void testPacketTransmission() {
        monitor.recordPacketTransmission(100.0);
        assertEquals(100.0, monitor.getTransmittedData());
    }

    @Test
    void testSuccessfulDelivery() {
        monitor.recordSuccessfulDelivery(10.0, 100.0);
        assertEquals(10.0, monitor.getAverageLatency());
        assertEquals(100.0, monitor.getBandwidthUtilization());
        assertEquals(1, monitor.getTotalPacketsProcessed());
    }

    @Test
    void testPacketLossRate() {
        monitor.recordSuccessfulDelivery(10.0, 100.0);
        monitor.recordFailedDelivery();
        assertEquals(0.5, monitor.getPacketLossRate());
    }
}

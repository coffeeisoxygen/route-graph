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
    void testMetricsCalculation() {
        // Simulate network traffic
        monitor.recordPacketDelivery(10, 100);
        monitor.recordPacketDelivery(20, 150);
        monitor.recordPacketLoss();

        assertEquals(15, monitor.getAverageLatency());
        assertEquals(125, monitor.getBandwidthUtilization());
        assertEquals(0.333, monitor.getPacketLossRate(), 0.001);
        assertEquals(2, monitor.getTotalPacketsProcessed());
    }
}

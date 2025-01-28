package com.coffeecode.metrics;

public interface NetworkMetrics {
    double getAverageLatency();

    double getBandwidthUtilization();

    double getPacketLossRate();

    long getTotalPacketsProcessed();

    double getNetworkThroughput();

    void recordPacketTransmission(double size);

    void recordSuccessfulDelivery(double latency, double bandwidth);

    void recordFailedDelivery();
}

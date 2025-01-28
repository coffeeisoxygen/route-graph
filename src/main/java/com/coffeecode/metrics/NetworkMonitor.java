package com.coffeecode.metrics;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.DoubleAdder;

import lombok.Getter;

@Getter
public class NetworkMonitor implements NetworkMetrics {
    private final DoubleAdder totalLatency = new DoubleAdder();
    private final AtomicLong packetCount = new AtomicLong(0);
    private final AtomicLong lostPackets = new AtomicLong(0);
    private final DoubleAdder totalBandwidth = new DoubleAdder();
    private final DoubleAdder transmittedData = new DoubleAdder();
    private final long startTime = System.currentTimeMillis();

    @Override
    public void recordPacketTransmission(double size) {
        transmittedData.add(size);
    }

    @Override
    public void recordSuccessfulDelivery(double latency, double bandwidth) {
        totalLatency.add(latency);
        totalBandwidth.add(bandwidth);
        packetCount.incrementAndGet();
    }

    @Override
    public void recordFailedDelivery() {
        lostPackets.incrementAndGet();
    }

    @Override
    public double getAverageLatency() {
        return packetCount.get() > 0 ? totalLatency.sum() / packetCount.get() : 0;
    }

    @Override
    public double getBandwidthUtilization() {
        return packetCount.get() > 0 ? totalBandwidth.sum() / packetCount.get() : 0;
    }

    @Override
    public double getPacketLossRate() {
        long total = packetCount.get() + lostPackets.get();
        return total > 0 ? (double) lostPackets.get() / total : 0;
    }

    @Override
    public long getTotalPacketsProcessed() {
        return packetCount.get();
    }

    @Override
    public double getNetworkThroughput() {
        long duration = System.currentTimeMillis() - startTime;
        return duration > 0 ? (transmittedData.sum() * 1000.0) / duration : 0;
    }

    public double getTransmittedData() {
        return transmittedData.sum();
    }
}

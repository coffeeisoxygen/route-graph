// package com.coffeecode.utils;

// public class MetricsCollector {
//     private int totalPackets;
//     private int lostPackets;
//     private long totalLatency;

//     public MetricsCollector() {
//         this.totalPackets = 0;
//         this.lostPackets = 0;
//         this.totalLatency = 0;
//     }

//     public void recordPacketSent() {
//         totalPackets++;
//     }

//     public void recordPacketLost() {
//         lostPackets++;
//     }

//     public void recordLatency(long latency) {
//         totalLatency += latency;
//     }

//     public int getTotalPackets() {
//         return totalPackets;
//     }

//     public int getLostPackets() {
//         return lostPackets;
//     }

//     public long getAverageLatency() {
//         return totalPackets > 0 ? totalLatency / totalPackets : 0;
//     }

//     public void resetMetrics() {
//         totalPackets = 0;
//         lostPackets = 0;
//         totalLatency = 0;
//     }
// }

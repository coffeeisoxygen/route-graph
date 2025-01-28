package com.coffeecode.network.calculator.core;

public interface HeadLossCalculator {
    /**
     * Calculate total head loss for a pipe
     *
     * @param diameter      Pipe diameter (m)
     * @param length        Pipe length (m)
     * @param roughness     Pipe roughness
     * @param velocity      Flow velocity (m/s)
     * @param elevationDiff Elevation difference (m)
     * @return Total head loss (m)
     */
    double calculateTotalHead(double diameter, double length,
            double roughness, double velocity,
            double elevationDiff);
}

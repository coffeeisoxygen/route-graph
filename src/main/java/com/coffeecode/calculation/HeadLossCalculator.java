package com.coffeecode.calculation;

import com.coffeecode.network.edges.Pipe;

public interface HeadLossCalculator {
    double calculateTotalHead(Pipe pipe);
}

package com.coffeecode.calculation;

import com.coffeecode.config.AppProperties;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HeadLossFactory {
    private final AppProperties config;

    public HeadLossCalculator createCalculator() {
        return new DarcyWeisbachCalculator(config);
    }
}

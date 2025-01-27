package com.coffeecode.calculation;

import com.coffeecode.config.AppProperties;

public class TestConfig extends AppProperties {
    private final double defaultVelocity;
    private final double gravity;

    public TestConfig() {
        this(1.5, 9.81);
    }

    public TestConfig(double defaultVelocity, double gravity) {
        super(); // Explicitly call the default constructor of AppProperties
        this.defaultVelocity = defaultVelocity;
        this.gravity = gravity;
    }

    @Override
    public double getDouble(String key) {
        return switch (key) {
            case "water.velocity.default" -> defaultVelocity;
            case "environment.gravity" -> gravity;
            default -> 0.0;
        };
    }
}

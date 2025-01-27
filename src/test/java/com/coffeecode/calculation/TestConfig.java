package com.coffeecode.calculation;

import com.coffeecode.config.AppProperties;

public class TestConfig extends AppProperties {
    private final double defaultVelocity;
    private final double minVelocity;
    private final double maxVelocity;
    private final double kinematicViscosity;
    private final double gravity;

    public TestConfig() {
        this(1.5, 0.3, 3.0, 1.004E-6, 9.81);
    }

    public TestConfig(double defaultVelocity, double minVelocity, double maxVelocity,
            double kinematicViscosity, double gravity) {
        super(); // Explicitly call the default constructor of AppProperties
        this.defaultVelocity = defaultVelocity;
        this.minVelocity = minVelocity;
        this.maxVelocity = maxVelocity;
        this.kinematicViscosity = kinematicViscosity;
        this.gravity = gravity;
    }

    @Override
    public double getDouble(String key) {
        return switch (key) {
            case "water.velocity.default" -> defaultVelocity;
            case "water.velocity.min" -> minVelocity;
            case "water.velocity.max" -> maxVelocity;
            case "water.kinematic.viscosity" -> kinematicViscosity;
            case "environment.gravity" -> gravity;
            default -> throw new IllegalArgumentException("Property not found: " + key);
        };
    }
}

package com.coffeecode.network.calculator.physics.properties;

public enum FlowRegime {
    LAMINAR(0, 2300),
    TRANSITION(2300, 4000),
    TURBULENT(4000, Double.MAX_VALUE);

    private final double minReynolds;
    private final double maxReynolds;

    FlowRegime(double minReynolds, double maxReynolds) {
        this.minReynolds = minReynolds;
        this.maxReynolds = maxReynolds;
    }

    public static FlowRegime fromReynolds(double reynolds) {
        for (FlowRegime regime : values()) {
            if (reynolds >= regime.minReynolds && reynolds < regime.maxReynolds) {
                return regime;
            }
        }
        return TURBULENT;
    }
}

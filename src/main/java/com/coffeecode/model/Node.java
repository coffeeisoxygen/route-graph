package com.coffeecode.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class Node {
    protected double x;
    protected double y;
    protected double elevation;
    protected double velocity;
    protected double pressure;

    public Node(double x, double y, double elevation, double velocity, double pressure) {
        this.x = x;
        this.y = y;
        this.elevation = elevation;
        this.velocity = velocity;
        this.pressure = pressure;
    }

    public double getDistanceTo(Node other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}

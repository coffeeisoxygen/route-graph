package com.coffeecode.model;

import lombok.Value;

/**
 * Represents a pipe segment in a hydraulic network.
 * All measurements are in SI units (meters).
 */
@Value
public class Pipe {
    private static final double MIN_DIAMETER = 0.05; // meters
    private static final double MAX_DIAMETER = 2.0; // meters

    Node start;
    Node end;
    double length;
    double diameter;
    PipeMaterial material;

    private Pipe(PipeBuilder builder) {
        validateBuilder(builder);
        this.start = builder.start;
        this.end = builder.end;
        this.diameter = builder.diameter;
        this.material = builder.material;
        this.length = builder.length > 0 ? builder.length : calculateLength(builder.start, builder.end);
    }

    private static void validateBuilder(PipeBuilder builder) {
        if (builder == null) {
            throw new IllegalArgumentException("Builder cannot be null");
        }
        validateNodes(builder.start, builder.end);
        validateDimensions(builder.diameter);
        validateMaterial(builder.material);
    }

    private static void validateNodes(Node start, Node end) {
        if (start == null) {
            throw new IllegalArgumentException("Start node cannot be null");
        }
        if (end == null) {
            throw new IllegalArgumentException("End node cannot be null");
        }
        if (start.equals(end)) {
            throw new IllegalArgumentException("Start and end nodes cannot be the same");
        }
    }

    private static void validateDimensions(double diameter) {
        if (diameter <= 0) {
            throw new IllegalArgumentException("Diameter must be positive");
        }
        if (diameter < MIN_DIAMETER || diameter > MAX_DIAMETER) {
            throw new IllegalArgumentException(
                    String.format("Diameter must be between %.2f and %.2f meters",
                            MIN_DIAMETER, MAX_DIAMETER));
        }
    }

    private static void validateMaterial(PipeMaterial material) {
        if (material == null) {
            throw new IllegalArgumentException("Material cannot be null");
        }
    }

    private static double calculateLength(Node start, Node end) {
        return start.getDistanceTo(end) * 1000; // convert km to m
    }

    public double getElevationDifference() {
        return end.getElevation() - start.getElevation();
    }

    public double getRoughness() {
        return material.getRoughness();
    }

    public static PipeBuilder builder() {
        return new PipeBuilder();
    }

    public static class PipeBuilder {
        private Node start;
        private Node end;
        private double length;
        private double diameter;
        private PipeMaterial material;

        public PipeBuilder start(Node start) {
            this.start = start;
            return this;
        }

        public PipeBuilder end(Node end) {
            this.end = end;
            return this;
        }

        public PipeBuilder length(double length) {
            this.length = length;
            return this;
        }

        public PipeBuilder diameter(double diameter) {
            this.diameter = diameter;
            return this;
        }

        public PipeBuilder material(PipeMaterial material) {
            this.material = material;
            return this;
        }

        public Pipe build() {
            return new Pipe(this);
        }
    }
}

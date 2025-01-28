package com.coffeecode.model;

import com.coffeecode.calculation.validation.PipeValidator;

import lombok.Value;

/**
 * Represents a pipe segment in a hydraulic network.
 * All measurements are in SI units (meters).
 */
@Value
public class Pipe {
    private static final PipeValidator validator = new PipeValidator();

    Node start;
    Node end;
    double length;
    double diameter;
    PipeMaterial material;

    private Pipe(PipeBuilder builder) {
        validator.validateBuilder(builder.start, builder.end,
                builder.diameter, builder.material);
        this.start = builder.start;
        this.end = builder.end;
        this.diameter = builder.diameter;
        this.material = builder.material;
        this.length = builder.length > 0 ? builder.length : calculateLength(builder.start, builder.end);
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

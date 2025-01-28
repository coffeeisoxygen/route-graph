package com.coffeecode.network.calculator.validation;

import com.coffeecode.network.edges.PipeMaterial;
import com.coffeecode.network.nodes.Node;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PipeValidator {
    private static final double MIN_DIAMETER = 0.05;
    private static final double MAX_DIAMETER = 2.0;

    public void validateBuilder(Node start, Node end, double diameter, PipeMaterial material) {
        validateNodes(start, end);
        validateDiameter(diameter);
        validateMaterial(material);
        log.debug("Pipe validation successful");
    }

    private void validateNodes(Node start, Node end) {
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

    private void validateDiameter(double diameter) {
        if (diameter <= 0) {
            throw new IllegalArgumentException("Diameter must be positive");
        }
        if (diameter < MIN_DIAMETER || diameter > MAX_DIAMETER) {
            throw new IllegalArgumentException(
                    String.format("Diameter must be between %.2f and %.2f meters",
                            MIN_DIAMETER, MAX_DIAMETER));
        }
    }

    private void validateMaterial(PipeMaterial material) {
        if (material == null) {
            throw new IllegalArgumentException("Material cannot be null");
        }
    }
}

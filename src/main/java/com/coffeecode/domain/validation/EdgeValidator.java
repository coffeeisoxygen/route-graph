package com.coffeecode.domain.validation;

import org.springframework.stereotype.Component;

import com.coffeecode.domain.model.NetworkEdge;

@Component
public class EdgeValidator extends BaseValidator {

        public ValidationResult validateEdge(NetworkEdge edge) {
                return validate(
                                () -> requireNonNull(edge, "Edge cannot be null"),
                                () -> validateNodes(edge),
                                () -> validateProperties(edge));
        }

        private void validateNodes(NetworkEdge edge) {
                require(edge.getSource() != null && edge.getDestination() != null,
                                "Edge nodes cannot be null");
                require(edge.getSource().isActive() && edge.getDestination().isActive(),
                                "Edge nodes must be active");
                require(!edge.getSource().equals(edge.getDestination()),
                                "Self-loops are not allowed");
        }

        private void validateProperties(NetworkEdge edge) {
                var props = requireNonNull(edge.getProperties(),
                                "Edge properties cannot be null");
                require(props.getBandwidth() != null && props.getBandwidth() > 0,
                                "Bandwidth must be positive");
                require(props.getLatency() != null && props.getLatency() >= 0,
                                "Latency cannot be negative");
        }
}

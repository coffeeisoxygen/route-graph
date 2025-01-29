package com.coffeecode.domain.validation;

import org.springframework.stereotype.Component;

import com.coffeecode.domain.model.NetworkEdge;
import com.coffeecode.domain.model.NetworkNode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NetworkValidator extends BaseValidator {
    private final EdgeValidator edgeValidator;

    public ValidationResult validateNode(NetworkNode node) {
        return validate(
                () -> requireNonNull(node, "Node cannot be null"),
                () -> require(node.isActive(), "Node must be active"));
    }

    public ValidationResult validateConnection(NetworkNode source, NetworkNode target, NetworkEdge edge) {
        return validate(
                () -> validateSourceNode(source),
                () -> validateTargetNode(target),
                () -> validateConnectionRules(source, target),
                () -> edgeValidator.validateEdge(edge));
    }

    private void validateSourceNode(NetworkNode source) {
        requireValid(validateNode(source));
        require(source.getType().canInitiateConnection(),
                String.format("%s nodes cannot initiate connections", source.getType()));
    }

    private void validateTargetNode(NetworkNode target) {
        requireValid(validateNode(target));
    }

    private void validateConnectionRules(NetworkNode source, NetworkNode target) {
        require(!source.equals(target), "Self-connections are not allowed");
    }

    public ValidationResult validateMaxConnections(NetworkNode node, int maxConnections) {
        return validate(() -> {
            if (maxConnections > 0 && node.getConnections().size() >= maxConnections) {
                throw new ValidationException(
                        String.format("Node %s has reached maximum connections (%d)",
                                node.getIdentity().getName(),
                                maxConnections));
            }
        });
    }
}

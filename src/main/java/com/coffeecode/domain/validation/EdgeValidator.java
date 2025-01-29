package com.coffeecode.domain.validation;

import org.springframework.stereotype.Component;

import com.coffeecode.domain.edge.properties.NetEdgeProperties;
import com.coffeecode.domain.node.base.NetNode;

@Component
public class EdgeValidator {
    public void validate(NetNode source, NetNode target, NetEdgeProperties props) {
        validateNull(source, target, props);
        validateState(source, target);
        validateProperties(props);
        validateRules(source, target);
    }

    private void validateNull(NetNode source, NetNode target, NetEdgeProperties props) {
        if (props == null)
            throw new IllegalArgumentException("Edge properties cannot be null");
        if (source == null || target == null)
            throw new IllegalArgumentException("Nodes cannot be null");
    }

    private void validateState(NetNode source, NetNode target) {
        if (!source.isActive() || !target.isActive()) {
            throw new IllegalArgumentException("Both nodes must be active");
        }
    }

    private void validateProperties(NetEdgeProperties props) {
        if (!props.isValid())
            throw new IllegalArgumentException("Invalid edge properties");
        if (props.getBandwidth() <= 0 || props.getLatency() < 0) {
            throw new IllegalArgumentException("Invalid edge metrics");
        }
    }

    private void validateRules(NetNode source, NetNode target) {
        if (source.equals(target))
            throw new IllegalArgumentException("Self-connections not allowed");
    }
}

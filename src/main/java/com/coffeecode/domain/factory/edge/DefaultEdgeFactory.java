package com.coffeecode.domain.factory.edge;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.coffeecode.domain.common.Identity;
import com.coffeecode.domain.edge.NetEdge;
import com.coffeecode.domain.edge.properties.NetEdgeProperties;
import com.coffeecode.domain.node.base.NetNode;

@Component
public class DefaultEdgeFactory implements NetEdgeFactory {

    @Override
    public NetEdge createEdge(NetNode source, NetNode target, NetEdgeProperties props) {
        validateEdgeCreation(source, target, props);
        validateEdgeRules(source, target, props);
        NetEdge edge = NetEdge.builder()
                .identity(Identity.create("edge")) // Add identity creation
                .source(source)
                .destination(target)
                .properties(props)
                .active(true)
                .build();
        source.addConnection(edge);
        return edge;
    }

    private void validateEdgeCreation(NetNode source, NetNode target, NetEdgeProperties props) {
        if (props == null) {
            throw new IllegalArgumentException("Edge properties cannot be null");
        }
        if (!props.isValid()) {
            throw new IllegalArgumentException("Invalid edge properties");
        }
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source and target nodes must not be null");
        }
        if (!source.isActive() || !target.isActive()) {
            throw new IllegalArgumentException("Both nodes must be active");
        }
    }

    private void validateEdgeRules(NetNode source, NetNode target, NetEdgeProperties props) {
        // Connection rules
        if (source.equals(target)) {
            throw new IllegalArgumentException("Self-connections not allowed");
        }

        // Capacity checks
        // if (!source.canInitiateConnection()) {
        // throw new IllegalArgumentException("Source node cannot initiate
        // connections");
        // }

        // Property validation
        if (props.getBandwidth() <= 0 || props.getLatency() < 0) {
            throw new IllegalArgumentException("Invalid edge metrics");
        }
    }

    @Override
    public List<NetEdge> createBidirectional(NetNode nodeA, NetNode nodeB,
            NetEdgeProperties props) {
        List<NetEdge> edges = new ArrayList<>();
        edges.add(createEdge(nodeA, nodeB, props));
        edges.add(createEdge(nodeB, nodeA, props));
        return edges;
    }

    @Override
    public List<NetEdge> connectAll(List<NetNode> nodes, NetEdgeProperties props) {
        List<NetEdge> edges = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                edges.addAll(createBidirectional(nodes.get(i), nodes.get(j), props));
            }
        }
        return edges;
    }
}

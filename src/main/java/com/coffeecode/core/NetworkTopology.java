package com.coffeecode.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coffeecode.validation.NetworkValidator;
import com.coffeecode.validation.ValidationError;

import lombok.Getter;

@Getter
public class NetworkTopology {
    private final Map<String, Node> nodes;
    private final List<Edge> edges;
    private final NetworkValidator validator;

    public NetworkTopology(NetworkValidator validator) {
        this.nodes = new HashMap<>();
        this.edges = new ArrayList<>();
        this.validator = validator;
    }

    public void addNode(Node node) {
        if (node == null || nodes.containsKey(node.getId())) {
            throw new IllegalArgumentException("Invalid node or duplicate ID");
        }
        nodes.put(node.getId(), node);
        validateState();
    }

    public void connect(String sourceId, String destId, double bandwidth, double latency) {
        Node source = nodes.get(sourceId);
        Node dest = nodes.get(destId);

        if (source == null || dest == null) {
            throw new IllegalArgumentException("Source or destination node not found");
        }

        Edge edge = Edge.builder()
                .source(source)
                .destination(dest)
                .bandwidth(bandwidth)
                .latency(latency)
                .active(true)
                .build();

        if (edge.isValid()) {
            edges.add(edge);
            source.addEdge(edge);
            validateState();
        }
    }

    public List<Node> getNeighbors(String nodeId) {
        Node node = nodes.get(nodeId);
        if (node == null) {
            throw new IllegalArgumentException("Node not found");
        }

        return node.getEdges().stream()
                .map(Edge::getDestination)
                .filter(Node::isActive)
                .toList();
    }

    public boolean isConnected(String sourceId, String destId) {
        return edges.stream()
                .anyMatch(e -> e.getSource().getId().equals(sourceId)
                        && e.getDestination().getId().equals(destId)
                        && e.isConnected());
    }

    private void validateState() {
        List<ValidationError> errors = validator.validate(this);
        if (!validator.isValid(this)) {
            throw new IllegalStateException("Invalid network state: " + errors);
        }
    }
}

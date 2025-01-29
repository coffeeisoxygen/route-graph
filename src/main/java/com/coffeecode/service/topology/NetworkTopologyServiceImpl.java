package com.coffeecode.service.topology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.coffeecode.domain.entities.edge.Edge;
import com.coffeecode.domain.entities.node.base.Node;
import com.coffeecode.domain.topology.NetworkTopology;
import com.coffeecode.infrastructure.validation.NetworkValidator;
import com.coffeecode.infrastructure.validation.ValidationError;
import com.coffeecode.infrastructure.validation.exception.NetworkValidationException;

import lombok.Getter;

@Service
@Getter
public class NetworkTopologyServiceImpl implements NetworkTopologyService, NetworkTopology {
    private final Map<String, Node> nodes;
    private final List<Edge> edges;
    private final NetworkValidator validator;

    public NetworkTopologyServiceImpl(NetworkValidator validator) {
        this.nodes = new HashMap<>();
        this.edges = new ArrayList<>();
        this.validator = validator;
    }

    @Override
    public void addNode(Node node) {
        if (node == null || nodes.containsKey(node.getId().toString())) {
            throw new IllegalArgumentException("Invalid node or duplicate ID");
        }
        nodes.put(node.getId().toString(), node);
        validateState();
    }

    @Override
    public void addEdge(Edge edge) {
        if (edge.isValid()) {
            edges.add(edge);
            edge.getSource().addEdge(edge);
            validateState();
        }
    }

    @Override
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

    @Override
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

    @Override
    public Optional<Node> getNode(String id) {
        return Optional.ofNullable(nodes.get(id));
    }

    @Override
    public boolean isConnected(String sourceId, String destId) {
        return edges.stream()
                .anyMatch(e -> e.getSource().getId().toString().equals(sourceId)
                        && e.getDestination().getId().toString().equals(destId)
                        && e.isConnected());
    }

    // Add proper exception handling
    private void validateState() {
        try {
            List<ValidationError> errors = validator.validate(this);
            if (!validator.isValid(this)) {
                throw new NetworkValidationException("INVALID_STATE", "Invalid network state: " + errors);
            }
        } catch (Exception e) {
            throw new NetworkValidationException("VALIDATION_ERROR", e.getMessage());
        }
    }
}

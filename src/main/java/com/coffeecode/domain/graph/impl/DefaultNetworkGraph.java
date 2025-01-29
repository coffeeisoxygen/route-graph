package com.coffeecode.domain.graph.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.coffeecode.domain.edge.NetworkEdge;
import com.coffeecode.domain.graph.NetworkGraph;
import com.coffeecode.domain.graph.model.PathNode;
import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.node.NetworkNode;
import com.coffeecode.domain.properties.EdgeProperties;

import lombok.Getter;

/**
 * Thread-safe implementation of NetworkGraph using adjacency list
 * representation.
 * Manages network topology and provides path finding capabilities.
 */
@Getter
public class DefaultNetworkGraph implements NetworkGraph {
    private final Map<NetworkIdentity, NetworkNode> nodes;
    private final Map<NetworkIdentity, Set<NetworkEdge>> adjacencyList;

    public DefaultNetworkGraph() {
        this.nodes = new ConcurrentHashMap<>();
        this.adjacencyList = new ConcurrentHashMap<>();
    }

    @Override
    public void addNode(NetworkNode node) {
        if (node == null || !node.isActive()) {
            throw new IllegalArgumentException("Node must be non-null and active");
        }
        nodes.putIfAbsent(node.getIdentity(), node);
        adjacencyList.putIfAbsent(node.getIdentity(), ConcurrentHashMap.newKeySet());
    }

    @Override
    public void connect(NetworkNode source, NetworkNode target, EdgeProperties props) {
        validateConnection(source, target);

        NetworkEdge edge = NetworkEdge.builder()
                .source(source)
                .destination(target)
                .properties(props)
                .build();

        if (!edge.isValid()) {
            throw new IllegalArgumentException("Invalid edge configuration");
        }

        adjacencyList.get(source.getIdentity()).add(edge);
    }

    @Override
    public Optional<List<NetworkNode>> findPath(NetworkNode source, NetworkNode target) {
        if (!isValidPathRequest(source, target)) {
            return Optional.empty();
        }

        return findShortestPath(source.getIdentity(), target.getIdentity());
    }

    private void validateConnection(NetworkNode source, NetworkNode target) {
        if (!nodes.containsKey(source.getIdentity()) ||
                !nodes.containsKey(target.getIdentity())) {
            throw new IllegalArgumentException("Both nodes must exist in the graph");
        }

        if (!source.canInitiateConnection() || !target.canAcceptConnection()) {
            throw new IllegalArgumentException("Invalid connection state");
        }
    }

    private boolean isValidPathRequest(NetworkNode source, NetworkNode target) {
        return source != null &&
                target != null &&
                nodes.containsKey(source.getIdentity()) &&
                nodes.containsKey(target.getIdentity()) &&
                source.isActive() &&
                target.isActive();
    }

    private Optional<List<NetworkNode>> findShortestPath(NetworkIdentity source, NetworkIdentity target) {
        Map<NetworkIdentity, PathNode> pathNodes = new HashMap<>();
        PriorityQueue<PathNode> queue = new PriorityQueue<>();

        // Initialize
        nodes.keySet().forEach(id -> {
            PathNode pathNode = id.equals(source) ? PathNode.create(id).withDistance(0.0) : PathNode.create(id);
            pathNodes.put(id, pathNode);
            queue.offer(pathNode);
        });

        while (!queue.isEmpty()) {
            PathNode current = queue.poll();

            if (current.getId().equals(target)) {
                return buildPath(pathNodes, source, target);
            }

            if (current.getDistance() == Double.POSITIVE_INFINITY) {
                break;
            }

            processNeighbors(current, pathNodes, queue);
        }

        return Optional.empty();
    }

    private void processNeighbors(
            PathNode current,
            Map<NetworkIdentity, PathNode> pathNodes,
            PriorityQueue<PathNode> queue) {

        Set<NetworkEdge> edges = adjacencyList.get(current.getId());
        for (NetworkEdge edge : edges) {
            if (!edge.isActive())
                continue;

            NetworkIdentity neighborId = edge.getDestination().getIdentity();
            double newDistance = current.getDistance() + calculateEdgeWeight(edge);

            PathNode neighbor = pathNodes.get(neighborId);
            if (newDistance < neighbor.getDistance()) {
                queue.remove(neighbor);
                PathNode updated = neighbor.withDistance(newDistance)
                        .withPrevious(current.getId());
                pathNodes.put(neighborId, updated);
                queue.offer(updated);
            }
        }
    }

    private double calculateEdgeWeight(NetworkEdge edge) {
        return edge.getProperties().getLatency();
    }

    private Optional<List<NetworkNode>> buildPath(
            Map<NetworkIdentity, PathNode> pathNodes,
            NetworkIdentity source,
            NetworkIdentity target) {
        List<NetworkNode> path = new ArrayList<>();
        NetworkIdentity current = target;

        while (current != null) {
            path.add(0, nodes.get(current));
            PathNode pathNode = pathNodes.get(current);
            current = pathNode.getPrevious();
        }

        return path.get(0).getIdentity().equals(source) ? Optional.of(path) : Optional.empty();
    }
}

package com.coffeecode.domain.topology;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.coffeecode.domain.edge.NetEdge;
import com.coffeecode.domain.node.base.NetNode;

import lombok.Getter;

@Component
@Getter
public class DefaultNetworkTopology implements NetworkTopology {
    private final Map<String, NetNode> nodes;
    private final List<NetEdge> edges;
    private boolean active;
    private double networkLoad;

    public DefaultNetworkTopology() {
        this.nodes = new ConcurrentHashMap<>();
        this.edges = Collections.synchronizedList(new ArrayList<>());
        this.active = true;
        this.networkLoad = 0.0;
    }

    // Graph Management
    @Override
    public synchronized void addNode(NetNode node) {
        validateNode(node);
        nodes.put(node.getIdentity().getId().toString(), node);
    }

    @Override
    public synchronized void addEdge(NetEdge edge) {
        validateEdge(edge);
        edges.add(edge);
    }

    @Override
    public Optional<NetNode> findNode(String nodeId) {
        return Optional.ofNullable(nodes.get(nodeId));
    }

    @Override
    public synchronized boolean removeNode(String nodeId) {
        NetNode node = nodes.remove(nodeId);
        if (node != null) {
            edges.removeIf(edge -> edge.getSource().getIdentity().getId().toString().equals(nodeId) ||
                    edge.getDestination().getIdentity().getId().toString().equals(nodeId));
            return true;
        }
        return false;
    }

    // Edge Operations
    @Override
    public Optional<NetEdge> findEdge(String sourceId, String destId) {
        return edges.stream()
                .filter(edge -> edge.getSource().getIdentity().getId().toString().equals(sourceId) &&
                        edge.getDestination().getIdentity().getId().toString().equals(destId))
                .findFirst();
    }

    @Override
    public synchronized boolean removeEdge(NetEdge edge) {
        return edges.remove(edge);
    }

    // Path Operations
    @Override
    public boolean isConnected(String sourceId, String destId) {
        NetNode source = nodes.get(sourceId);
        NetNode dest = nodes.get(destId);
        if (source == null || dest == null)
            return false;

        Set<String> visited = new HashSet<>();
        return hasPath(source, dest, visited);
    }

    @Override
    public List<NetEdge> findPath(String sourceId, String destId) {
        NetNode source = nodes.get(sourceId);
        NetNode dest = nodes.get(destId);
        if (source == null || dest == null)
            return Collections.emptyList();

        return findShortestPath(source, dest);
    }

    // Network Analytics
    @Override
    public int getNodeCount() {
        return nodes.size();
    }

    @Override
    public int getEdgeCount() {
        return edges.size();
    }

    @Override
    public double getNetworkLoad() {
        return calculateNetworkLoad();
    }

    // Helper Methods
    private void validateNode(NetNode node) {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (nodes.containsKey(node.getIdentity().getId().toString())) {
            throw new IllegalArgumentException("Node already exists in topology");
        }
    }

    private void validateEdge(NetEdge edge) {
        if (edge == null || !edge.isValid()) {
            throw new IllegalArgumentException("Invalid edge");
        }
        String sourceId = edge.getSource().getIdentity().getId().toString();
        String destId = edge.getDestination().getIdentity().getId().toString();

        if (!nodes.containsKey(sourceId) || !nodes.containsKey(destId)) {
            throw new IllegalArgumentException("Source or destination node not found");
        }
    }

    private boolean hasPath(NetNode source, NetNode dest, Set<String> visited) {
        String sourceId = source.getIdentity().getId().toString();
        if (source.equals(dest))
            return true;
        if (visited.contains(sourceId))
            return false;

        visited.add(sourceId);
        return edges.stream()
                .filter(edge -> edge.getSource().equals(source))
                .map(NetEdge::getDestination)
                .anyMatch(node -> hasPath(node, dest, visited));
    }

    private List<NetEdge> findShortestPath(NetNode source, NetNode dest) {
        // Implement Dijkstra's algorithm here
        // Will be implemented in next iteration
        return Collections.emptyList();
    }

    private double calculateNetworkLoad() {
        if (edges.isEmpty())
            return 0.0;

        return edges.stream()
                .mapToDouble(edge -> edge.getProperties().getBandwidth())
                .average()
                .orElse(0.0);
    }

    @Override
    public void setActive(boolean active) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setActive'");
    }
}

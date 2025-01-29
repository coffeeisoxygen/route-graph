package com.coffeecode.domain.topology.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.coffeecode.domain.edge.NetEdge;
import com.coffeecode.domain.node.base.NetNode;

@Component
public class NetPathFinder {
    private final NetGraph graph;

    public NetPathFinder(NetGraph graph) {
        this.graph = graph;
    }

    public boolean hasPath(NetNode source, NetNode destination) {
        Set<String> visited = new HashSet<>();
        return findPath(source, destination, visited);
    }

    private boolean findPath(NetNode source, NetNode dest, Set<String> visited) {
        String sourceId = source.getIdentity().getId().toString();
        if (source.equals(dest))
            return true;
        if (visited.contains(sourceId))
            return false;

        visited.add(sourceId);
        return graph.getEdges().stream()
                .filter(edge -> edge.getSource().equals(source))
                .map(NetEdge::getDestination)
                .anyMatch(node -> findPath(node, dest, visited));
    }

    public List<NetEdge> findShortestPath(NetNode source, NetNode dest) {
        Map<String, Double> distances = new HashMap<>();
        Map<String, NetEdge> previousEdges = new HashMap<>();
        PriorityQueue<NetNode> queue = new PriorityQueue<>(
                Comparator.comparingDouble(
                        node -> distances.getOrDefault(node.getIdentity().getId().toString(), Double.MAX_VALUE)));

        String sourceId = source.getIdentity().getId().toString();
        distances.put(sourceId, 0.0);
        queue.offer(source);

        while (!queue.isEmpty()) {
            NetNode current = queue.poll();
            String currentId = current.getIdentity().getId().toString();

            if (current.equals(dest)) {
                return buildPath(previousEdges, source, dest);
            }

            graph.getNodeEdges(currentId).forEach(edge -> {
                NetNode next = edge.getDestination();
                String nextId = next.getIdentity().getId().toString();
                double newDist = distances.get(currentId) + edge.getWeight();

                if (newDist < distances.getOrDefault(nextId, Double.MAX_VALUE)) {
                    distances.put(nextId, newDist);
                    previousEdges.put(nextId, edge);
                    queue.offer(next);
                }
            });
        }

        return Collections.emptyList();
    }

    private List<NetEdge> buildPath(Map<String, NetEdge> previousEdges,
            NetNode source, NetNode dest) {
        List<NetEdge> path = new ArrayList<>();
        NetNode current = dest;

        while (!current.equals(source)) {
            String currentId = current.getIdentity().getId().toString();
            NetEdge edge = previousEdges.get(currentId);
            path.add(0, edge);
            current = edge.getSource();
        }

        return path;
    }
}

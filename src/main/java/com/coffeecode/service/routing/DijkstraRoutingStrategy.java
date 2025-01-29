package com.coffeecode.service.routing;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;

import com.coffeecode.domain.entities.edge.Edge;
import com.coffeecode.domain.entities.node.base.Node;

import lombok.Getter;

@Getter
public class DijkstraRoutingStrategy implements RoutingStrategy {
    private final Map<Node, Double> distances = new HashMap<>();
    private final Map<Node, Node> previousNodes = new HashMap<>();
    private final PriorityQueue<Node> unvisitedNodes;

    public DijkstraRoutingStrategy() {
        this.unvisitedNodes = new PriorityQueue<>(
                Comparator.comparingDouble(node -> distances.getOrDefault(node, Double.MAX_VALUE)));
    }

    @Override
    public List<Node> findPath(Node source, Node destination) {
        if (!isValidEndpoints(source, destination)) {
            return Collections.emptyList();
        }

        initializeSearch(source);

        while (!unvisitedNodes.isEmpty()) {
            Node current = unvisitedNodes.poll();
            if (current.equals(destination)) {
                return buildPath(destination);
            }
            if (distances.get(current) == Double.MAX_VALUE) {
                break;
            }
            processNeighbors(current);
        }

        return Collections.emptyList();
    }

    private void initializeSearch(Node source) {
        distances.clear();
        previousNodes.clear();
        unvisitedNodes.clear();
        distances.put(source, 0.0);
        unvisitedNodes.add(source);
    }

    private void processNeighbors(Node current) {
        double currentDistance = distances.get(current);

        for (Edge edge : current.getEdges()) {
            if (!edge.isConnected())
                continue;

            Node neighbor = edge.getDestination();
            double newDistance = currentDistance + edge.getWeight();

            if (newDistance < distances.getOrDefault(neighbor, Double.MAX_VALUE)) {
                distances.put(neighbor, newDistance);
                previousNodes.put(neighbor, current);
                unvisitedNodes.add(neighbor);
            }
        }
    }

    private List<Node> buildPath(Node destination) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = destination;

        while (current != null) {
            path.addFirst(current);
            current = previousNodes.get(current);
        }

        return path;
    }

    @Override
    public double calculatePathCost(List<Node> path) {
        double cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Node current = path.get(i);
            Node next = path.get(i + 1);
            Optional<Edge> edge = current.getEdges().stream()
                    .filter(e -> e.getDestination().equals(next))
                    .findFirst();
            if (edge.isPresent()) {
                cost += edge.get().getWeight();
            }
        }
        return cost;
    }

    @Override
    public boolean isValidPath(List<Node> path) {
        if (path == null || path.size() < 2)
            return false;

        for (int i = 0; i < path.size() - 1; i++) {
            Node current = path.get(i);
            Node next = path.get(i + 1);
            boolean hasConnection = current.getEdges().stream()
                    .anyMatch(e -> e.getDestination().equals(next) && e.isConnected());
            if (!hasConnection)
                return false;
        }
        return true;
    }

    private boolean isValidEndpoints(Node source, Node destination) {
        return source != null && destination != null &&
                source.isActive() && destination.isActive();
    }

    @Override
    public String getAlgorithmName() {
        return "Dijkstra";
    }
}

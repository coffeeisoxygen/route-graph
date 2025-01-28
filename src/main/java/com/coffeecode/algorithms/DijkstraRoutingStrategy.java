package com.coffeecode.algorithms;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.coffeecode.core.Edge;
import com.coffeecode.core.Node;

public class DijkstraRoutingStrategy implements RoutingStrategy {
    private final Map<Node, Double> distances = new HashMap<>();
    private final Map<Node, Node> previousNodes = new HashMap<>();
    private final PriorityQueue<Node> unvisited = new PriorityQueue<>(
            Comparator.comparingDouble(distances::get));

    @Override
    public List<Node> findPath(Node source, Node destination) {
        if (!source.isActive() || !destination.isActive()) {
            return Collections.emptyList();
        }

        initialize(source);

        while (!unvisited.isEmpty()) {
            Node current = unvisited.poll();

            if (current.equals(destination)) {
                return buildPath(destination);
            }

            processNeighbors(current);
        }

        return Collections.emptyList();
    }

    private void initialize(Node source) {
        distances.clear();
        previousNodes.clear();
        unvisited.clear();

        distances.put(source, 0.0);
        unvisited.add(source);
    }

    private void processNeighbors(Node current) {
        for (Edge edge : current.getEdges()) {
            if (!edge.isConnected())
                continue;

            Node neighbor = edge.getDestination();
            double newDist = distances.get(current) + edge.getWeight();

            if (!distances.containsKey(neighbor) || newDist < distances.get(neighbor)) {
                distances.put(neighbor, newDist);
                previousNodes.put(neighbor, current);
                unvisited.add(neighbor);
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
}

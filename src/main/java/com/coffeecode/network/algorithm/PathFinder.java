package com.coffeecode.network.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.coffeecode.network.core.WaterNetwork;
import com.coffeecode.network.edges.Pipe;
import com.coffeecode.network.nodes.WaterDemand;
import com.coffeecode.network.nodes.WaterNodes;
import com.coffeecode.network.nodes.WaterSource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PathFinder {

    /**
     * Represents a path in the water network
     */
    @AllArgsConstructor
    @Getter
    public static class Path {
        private final List<WaterNodes> nodes;
        private final List<Pipe> pipes;
        private final double totalDistance;
        private final double totalElevationGain;
    }

    /**
     * Finds all possible paths from source to target
     */
    public List<Path> findPaths(WaterNetwork network, WaterSource source, WaterDemand target) {
        // Replace getNode() call with direct usage
        validateNodes(network, source, target);

        Map<WaterNodes, PathInfo> pathInfo = new HashMap<>();
        PriorityQueue<WaterNodes> queue = new PriorityQueue<>(
                Comparator.comparingDouble(node -> pathInfo.get(node).distance));

        // Use source directly since WaterSource extends WaterNodes
        initializePathInfo(network, source, pathInfo);
        queue.offer(source);

        // Process nodes
        while (!queue.isEmpty()) {
            WaterNodes current = queue.poll();

            if (current.equals(target)) {
                return reconstructPath(pathInfo, source, target);
            }

            processNeighbors(network, current, pathInfo, queue);
        }

        return Collections.emptyList();
    }

    /**
     * Validates source and target nodes exist in network
     */
    private void validateNodes(WaterNetwork network, WaterNodes source, WaterNodes target) {
        if (!network.getSources().contains((WaterSource) source)) {
            throw new IllegalArgumentException("Source node not in network");
        }
        if (!network.getDemands().contains((WaterDemand) target)) {
            throw new IllegalArgumentException("Target node not in network");
        }
    }

    private static class PathInfo {
        double distance;
        WaterNodes previous;
        Pipe pipe;

        PathInfo() {
            this.distance = Double.POSITIVE_INFINITY;
            this.previous = null;
            this.pipe = null;
        }
    }

    private void initializePathInfo(WaterNetwork network, WaterNodes source,
            Map<WaterNodes, PathInfo> pathInfo) {
        // Initialize for ALL nodes in network
        network.getSources().forEach(node -> pathInfo.put(node, new PathInfo()));
        network.getDemands().forEach(node -> pathInfo.put(node, new PathInfo()));

        // Set source distance to 0
        pathInfo.get(source).distance = 0;
    }

    private void processNeighbors(WaterNetwork network, WaterNodes current,
            Map<WaterNodes, PathInfo> pathInfo,
            PriorityQueue<WaterNodes> queue) {

        Map<WaterNodes, Pipe> neighbors = network.getAdjacencyMap().get(current);
        if (neighbors == null)
            return;

        for (Map.Entry<WaterNodes, Pipe> entry : neighbors.entrySet()) {
            WaterNodes neighbor = entry.getKey();
            // Add null check for neighbor
            pathInfo.computeIfAbsent(neighbor, k -> new PathInfo());

            Pipe pipe = entry.getValue();

            double newDistance = pathInfo.get(current).distance +
                    calculateWeight(pipe, current, neighbor);

            if (newDistance < pathInfo.get(neighbor).distance) {
                pathInfo.get(neighbor).distance = newDistance;
                pathInfo.get(neighbor).previous = current;
                pathInfo.get(neighbor).pipe = pipe;
                queue.offer(neighbor);
            }
        }
    }

    private double calculateWeight(Pipe pipe, WaterNodes from, WaterNodes to) {
        double distance = pipe.getLength();
        double elevationDiff = to.getElevation().getMeters() - from.getElevation().getMeters();

        // Penalize uphill paths more than downhill
        if (elevationDiff > 0) {
            distance += elevationDiff * 2;
        } else {
            distance += Math.abs(elevationDiff);
        }

        return distance;
    }

    private List<Path> reconstructPath(Map<WaterNodes, PathInfo> pathInfo,
            WaterNodes source, WaterNodes target) {
        List<WaterNodes> nodes = new ArrayList<>();
        List<Pipe> pipes = new ArrayList<>();
        double totalDistance = 0;
        double elevationGain = 0;

        WaterNodes current = target;
        while (current != null) {
            nodes.add(0, current);
            PathInfo info = pathInfo.get(current);

            if (info.pipe != null) {
                pipes.add(0, info.pipe);
                totalDistance += info.pipe.getLength();

                double elevationDiff = current.getElevation().getMeters() - info.previous.getElevation().getMeters();
                if (elevationDiff > 0) {
                    elevationGain += elevationDiff;
                }
            }

            current = info.previous;
        }

        return Collections.singletonList(new Path(nodes, pipes, totalDistance, elevationGain));
    }
}

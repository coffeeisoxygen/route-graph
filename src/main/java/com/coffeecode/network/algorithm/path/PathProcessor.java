package com.coffeecode.network.algorithm.path;

import java.util.Map;
import java.util.PriorityQueue;

import com.coffeecode.network.core.WaterNetwork;
import com.coffeecode.network.edges.Pipe;
import com.coffeecode.network.nodes.WaterNodes;

public final class PathProcessor {

    private PathProcessor() {
        // private constructor to prevent instantiation
    }

    public void processNeighbors(WaterNetwork network,
            WaterNodes current,
            Map<WaterNodes, PathInfo> pathInfo,
            PriorityQueue<WaterNodes> queue) {
        Map<WaterNodes, Pipe> neighbors = network.getAdjacencyMap().get(current);
        if (neighbors == null)
            return;

        neighbors.forEach((neighbor, pipe) -> {
            double newDistance = pathInfo.get(current).getDistance() +
                    calculateWeight(pipe, current, neighbor);

            if (newDistance < pathInfo.get(neighbor).getDistance()) {
                updatePathInfo(pathInfo, queue, neighbor, current, pipe, newDistance);
            }
        });
    }

    private double calculateWeight(Pipe pipe, WaterNodes from, WaterNodes to) {
        double distance = pipe.getLength();
        double elevationDiff = to.getElevation().getMeters() -
                from.getElevation().getMeters();

        return distance + (elevationDiff > 0 ? elevationDiff * 2 : Math.abs(elevationDiff));
    }

    private void updatePathInfo(Map<WaterNodes, PathInfo> pathInfo,
            PriorityQueue<WaterNodes> queue,
            WaterNodes neighbor,
            WaterNodes current,
            Pipe pipe,
            double newDistance) {
        PathInfo info = pathInfo.get(neighbor);
        info.setDistance(newDistance);
        info.setPrevious(current);
        info.setPipe(pipe);
        queue.offer(neighbor);
    }
}

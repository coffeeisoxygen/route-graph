package com.coffeecode.domain.graph.model;

import com.coffeecode.domain.model.NetworkIdentity;

import lombok.Value;

@Value
public class PathNode implements Comparable<PathNode> {
    NetworkIdentity id;
    double distance;
    NetworkIdentity previous;

    public static PathNode create(NetworkIdentity id) {
        return new PathNode(id, Double.POSITIVE_INFINITY, null);
    }

    public PathNode withDistance(double newDistance) {
        return new PathNode(id, newDistance, previous);
    }

    public PathNode withPrevious(NetworkIdentity newPrevious) {
        return new PathNode(id, distance, newPrevious);
    }

    @Override
    public int compareTo(PathNode other) {
        return Double.compare(this.distance, other.distance);
    }
}

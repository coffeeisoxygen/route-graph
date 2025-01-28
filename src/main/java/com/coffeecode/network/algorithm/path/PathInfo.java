package com.coffeecode.network.algorithm.path;

import com.coffeecode.network.edges.Pipe;
import com.coffeecode.network.nodes.WaterNodes;

import lombok.Data;

@Data
public class PathInfo {
    private double distance = Double.POSITIVE_INFINITY;
    private WaterNodes previous = null;
    private Pipe pipe = null;
}

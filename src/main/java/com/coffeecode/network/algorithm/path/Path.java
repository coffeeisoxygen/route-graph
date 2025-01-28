package com.coffeecode.network.algorithm.path;

import java.util.List;

import com.coffeecode.network.edges.Pipe;
import com.coffeecode.network.nodes.WaterNodes;

import lombok.Value;

@Value
public class Path {
    List<WaterNodes> nodes;
    List<Pipe> pipes;
    double totalDistance;
    double totalElevationGain;
}

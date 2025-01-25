package com.coffeecode.core.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import lombok.Getter;

@Getter
public class RouteData {

    private final Map<String, Location> locations;
    private final Map<UUID, Route> routes;
    private final Graph visualGraph;

    public RouteData() {
        this.locations = new HashMap<>();
        this.routes = new HashMap<>();
        this.visualGraph = new SingleGraph("RouteGraph");
        initializeGraph();
    }

    private void initializeGraph() {
        System.setProperty("org.graphstream.ui", "swing");
        visualGraph.setAttribute("ui.quality");
        visualGraph.setAttribute("ui.antialias");
    }
}

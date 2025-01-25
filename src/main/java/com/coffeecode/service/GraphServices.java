package com.coffeecode.service;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import com.coffeecode.core.model.Location;
import com.coffeecode.core.model.Route;

import lombok.Getter;

/**
 * Handles graph visualization and graph-specific operations Separates graph
 * logic from data management
 */
@Getter
public class GraphServices {

    private final Graph visualGraph;
    private static final String GRAPH_NAME = "RouteGraph";

    public GraphServices() {
        this.visualGraph = new SingleGraph(GRAPH_NAME);
        initializeGraph();
    }

    private void initializeGraph() {
        System.setProperty("org.graphstream.ui", "swing");
        visualGraph.setAttribute("ui.quality");
        visualGraph.setAttribute("ui.antialias");
        configureGraphStyle();
    }

    private void configureGraphStyle() {
        visualGraph.setAttribute("ui.stylesheet", """
            node {
                size: 10px;
                fill-color: #666666;
                text-size: 14;
            }
            node.start {
                fill-color: #45B649;
            }
            node.end {
                fill-color: #FF416C;
            }
            edge {
                fill-color: #666666;
                arrow-size: 5px;
            }
        """);
    }

    public void addLocationNode(Location location) {
        org.graphstream.graph.Node node = visualGraph.addNode(location.getIdLocation().toString());
        node.setAttribute("ui.label", location.getName());
        node.setAttribute("xy", location.getLongitude(), location.getLatitude());
    }

    public void removeLocationNode(Location location) {
        visualGraph.removeNode(location.getIdLocation().toString());
    }

    public void addRouteEdge(Route route) {
        String edgeId = route.getIdRoute().toString();
        String sourceId = route.getSource().getIdLocation().toString();
        String destId = route.getDestination().getIdLocation().toString();

        org.graphstream.graph.Edge edge = visualGraph.addEdge(edgeId, sourceId, destId,
                route.getType() == Route.RouteType.ONE_WAY);
        edge.setAttribute("ui.label", String.format("%.1f km", route.getDistance()));
    }

    public void removeRouteEdge(Route route) {
        visualGraph.removeEdge(route.getIdRoute().toString());
    }

    public void clear() {
        visualGraph.clear();
    }
}

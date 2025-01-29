package com.coffeecode.domain.factory.edge;

import com.coffeecode.domain.entities.edge.Edge;
import com.coffeecode.domain.entities.edge.EdgeProperties;
import com.coffeecode.domain.entities.node.base.Node;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;

@Component
public class DefaultEdgeFactory implements EdgeFactory {

    @Override
    public Edge createEdge(Node source, Node target, EdgeProperties props) {
        if (!props.isValid()) {
            throw new IllegalArgumentException("Invalid edge properties");
        }
        return Edge.builder()
                .source(source)
                .destination(target)
                .properties(props)
                .active(true)
                .build();
    }

    @Override
    public List<Edge> createBidirectional(Node nodeA, Node nodeB, EdgeProperties props) {
        List<Edge> edges = new ArrayList<>();
        edges.add(createEdge(nodeA, nodeB, props));
        edges.add(createEdge(nodeB, nodeA, props));
        return edges;
    }

    @Override
    public List<Edge> connectAll(List<Node> nodes, EdgeProperties props) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                edges.addAll(createBidirectional(nodes.get(i), nodes.get(j), props));
            }
        }
        return edges;
    }
}

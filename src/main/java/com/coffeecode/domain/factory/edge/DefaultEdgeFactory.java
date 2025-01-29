package com.coffeecode.domain.factory.edge;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.coffeecode.domain.common.NetID;
import com.coffeecode.domain.edge.NetEdge;
import com.coffeecode.domain.edge.properties.NetEdgeProperties;
import com.coffeecode.domain.node.base.NetNode;
import com.coffeecode.domain.validation.EdgeValidator;

@Component
public class DefaultEdgeFactory implements NetEdgeFactory {
    private final EdgeValidator validator;

    public DefaultEdgeFactory(EdgeValidator validator) {
        this.validator = validator;
    }

    @Override
    public NetEdge createEdge(NetNode source, NetNode target, NetEdgeProperties props) {
        validator.validate(source, target, props);
        NetEdge edge = NetEdge.builder()
                .identity(NetID.create("edge"))
                .source(source)
                .destination(target)
                .properties(props)
                .active(true)
                .build();
        source.addConnection(edge);
        return edge;
    }

    @Override
    public List<NetEdge> createBidirectional(NetNode nodeA, NetNode nodeB,
            NetEdgeProperties props) {
        List<NetEdge> edges = new ArrayList<>();
        edges.add(createEdge(nodeA, nodeB, props));
        edges.add(createEdge(nodeB, nodeA, props));
        return edges;
    }

    @Override
    public List<NetEdge> connectAll(List<NetNode> nodes, NetEdgeProperties props) {
        List<NetEdge> edges = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                edges.addAll(createBidirectional(nodes.get(i), nodes.get(j), props));
            }
        }
        return edges;
    }
}

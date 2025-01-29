package com.coffeecode.domain.factory.edge;

import java.util.List;

import com.coffeecode.domain.edge.NetEdge;
import com.coffeecode.domain.edge.properties.NetEdgeProperties;
import com.coffeecode.domain.node.base.NetNode;

public interface NetEdgeFactory {
    NetEdge createEdge(NetNode source, NetNode target, NetEdgeProperties props);

    List<NetEdge> createBidirectional(NetNode nodeA, NetNode nodeB, NetEdgeProperties props);

    List<NetEdge> connectAll(List<NetNode> nodes, NetEdgeProperties props);
}

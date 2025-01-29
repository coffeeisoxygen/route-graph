package com.coffeecode.domain.graph;

import java.util.List;
import java.util.Optional;

import com.coffeecode.domain.node.NetworkNode;
import com.coffeecode.domain.properties.EdgeProperties;

public interface NetworkGraph {
    void addNode(NetworkNode node);

    void connect(NetworkNode source, NetworkNode target, EdgeProperties props);

    Optional<List<NetworkNode>> findPath(NetworkNode source, NetworkNode target);
}

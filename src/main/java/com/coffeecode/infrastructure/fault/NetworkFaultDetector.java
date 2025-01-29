package com.coffeecode.infrastructure.fault;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.coffeecode.domain.entities.edge.Edge;
import com.coffeecode.domain.entities.node.base.Node;

import lombok.Getter;

@Getter
public class NetworkFaultDetector {
    private final Set<Node> failedNodes = ConcurrentHashMap.newKeySet();
    private final Set<Edge> failedLinks = ConcurrentHashMap.newKeySet();

    public void markNodeAsFailed(Node node) {
        node.setActive(false);
        failedNodes.add(node);
    }

    public void markLinkAsFailed(Edge edge) {
        edge.setActive(false);
        failedLinks.add(edge);
    }

    public void restoreNode(Node node) {
        node.setActive(true);
        failedNodes.remove(node);
    }

    public void restoreLink(Edge edge) {
        edge.setActive(true);
        failedLinks.remove(edge);
    }

    public boolean isNetworkHealthy() {
        return failedNodes.isEmpty() && failedLinks.isEmpty();
    }
}

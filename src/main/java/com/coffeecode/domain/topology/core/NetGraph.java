package com.coffeecode.domain.topology.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.coffeecode.domain.edge.NetEdge;
import com.coffeecode.domain.node.base.NetNode;

@Component
public class NetGraph {
    private final Map<String, NetNode> nodes;
    private final List<NetEdge> edges;

    public NetGraph() {
        this.nodes = new ConcurrentHashMap<>();
        this.edges = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized void addNode(NetNode node) {
        nodes.put(node.getIdentity().getId().toString(), node);
    }

    public synchronized void addEdge(NetEdge edge) {
        edges.add(edge);
    }

    public synchronized boolean removeNode(String nodeId) {
        NetNode node = nodes.remove(nodeId);
        if (node != null) {
            edges.removeIf(edge -> edge.getSource().getIdentity().getId().toString().equals(nodeId) ||
                    edge.getDestination().getIdentity().getId().toString().equals(nodeId));
            return true;
        }
        return false;
    }

    public synchronized boolean removeEdge(NetEdge edge) {
        return edges.remove(edge);
    }

    public boolean containsNode(String nodeId) {
        return nodes.containsKey(nodeId);
    }

    public List<NetEdge> getNodeEdges(String nodeId) {
        return edges.stream()
                .filter(edge -> edge.getSource().getIdentity().getId().toString().equals(nodeId) ||
                        edge.getDestination().getIdentity().getId().toString().equals(nodeId))
                .toList();
    }

    public Map<String, NetNode> getNodes() {
        return Collections.unmodifiableMap(nodes);
    }

    public List<NetEdge> getEdges() {
        return Collections.unmodifiableList(edges);
    }
}

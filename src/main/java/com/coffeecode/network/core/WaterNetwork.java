package com.coffeecode.network.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.coffeecode.network.edges.Pipe;
import com.coffeecode.network.nodes.WaterDemand;
import com.coffeecode.network.nodes.WaterNodes;
import com.coffeecode.network.nodes.WaterSource;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class WaterNetwork {
    private final Set<WaterSource> sources;
    private final Set<WaterDemand> demands;
    private final Set<Pipe> pipes;
    private final Map<WaterNodes, Map<WaterNodes, Pipe>> adjacencyMap;

    private WaterNetwork(Set<WaterSource> sources, Set<WaterDemand> demands,
            Set<Pipe> pipes, Map<WaterNodes, Map<WaterNodes, Pipe>> adjacencyMap) {
        this.sources = Collections.unmodifiableSet(new HashSet<>(sources));
        this.demands = Collections.unmodifiableSet(new HashSet<>(demands));
        this.pipes = Collections.unmodifiableSet(new HashSet<>(pipes));
        this.adjacencyMap = Collections.unmodifiableMap(new HashMap<>(adjacencyMap));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Set<WaterSource> sources = new HashSet<>();
        private final Set<WaterDemand> demands = new HashSet<>();
        private final Set<Pipe> pipes = new HashSet<>();
        private final Map<WaterNodes, Map<WaterNodes, Pipe>> adjacencyMap = new HashMap<>();

        public Builder addSource(WaterSource source) {
            sources.add(source);
            return this;
        }

        public Builder addDemand(WaterDemand demand) {
            demands.add(demand);
            return this;
        }

        public Builder connectNodes(WaterNodes from, WaterNodes to, Pipe pipe) {
            validateConnection(from, to);
            pipes.add(pipe);
            adjacencyMap.computeIfAbsent(from, k -> new HashMap<>()).put(to, pipe);
            log.debug("Connected nodes: {} -> {} with pipe {}", from.getName(), to.getName(), pipe);
            return this;
        }

        private void validateConnection(WaterNodes from, WaterNodes to) {
            if (from == null || to == null) {
                throw new IllegalArgumentException("Nodes cannot be null");
            }
            if (from.equals(to)) {
                throw new IllegalArgumentException("Cannot connect node to itself");
            }
            if (adjacencyMap.containsKey(from) && adjacencyMap.get(from).containsKey(to)) {
                throw new IllegalArgumentException("Connection already exists");
            }
        }

        public WaterNetwork build() {
            validateNetwork();
            return new WaterNetwork(sources, demands, pipes, adjacencyMap);
        }

        private void validateNetwork() {
            if (sources.isEmpty()) {
                throw new IllegalStateException("Network must have at least one source");
            }
            if (demands.isEmpty()) {
                throw new IllegalStateException("Network must have at least one demand");
            }
            validateConnectivity();
        }

        private void validateConnectivity() {
            Set<WaterNodes> reachableNodes = new HashSet<>();
            sources.forEach(source -> dfs(source, reachableNodes));

            if (!reachableNodes.containsAll(demands)) {
                throw new IllegalStateException("Not all demand nodes are reachable from sources");
            }
        }

        private void dfs(WaterNodes node, Set<WaterNodes> visited) {
            visited.add(node);
            if (adjacencyMap.containsKey(node)) {
                adjacencyMap.get(node).keySet().stream()
                        .filter(next -> !visited.contains(next))
                        .forEach(next -> dfs(next, visited));
            }
        }
    }
}

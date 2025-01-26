package com.coffeecode.domain.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.coffeecode.domain.objects.PipeProperties;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class WaterDistribution {

    private final Map<UUID, NetworkNode> nodes;
    private final Map<UUID, Pipe> pipes;
    private final WaterSource source;
    private final List<Customer> customers;

    public WaterDistribution(WaterSource source) {
        this.source = source;
        this.nodes = new HashMap<>();
        this.pipes = new HashMap<>();
        this.customers = new ArrayList<>();

        // Add source as first node
        addNode(source);
    }

    public void addNode(NetworkNode node) {
        nodes.put(node.getId(), node);
        if (node instanceof Customer customer) {
            customers.add(customer);
        }
        log.info("Added node: {}", node);
    }

    public Optional<Pipe> connectNodes(NetworkNode from, NetworkNode to, PipeProperties properties) {
        if (!nodes.containsKey(from.getId()) || !nodes.containsKey(to.getId())) {
            log.error("Cannot connect: one or both nodes not in network");
            return Optional.empty();
        }

        Pipe pipe = new Pipe(from, to, properties);
        pipes.put(pipe.getId(), pipe);
        log.info("Connected nodes with pipe: {}", pipe);
        return Optional.of(pipe);
    }

    private void addNode(WaterSource source) {
        nodes.put(source.getId(), source);
        log.info("Added source: {}", source);
    }
}

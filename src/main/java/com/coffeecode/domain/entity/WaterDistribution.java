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

/**
 * The WaterDistribution class represents a water distribution network. It
 * manages nodes (such as water sources and customers) and pipes connecting
 * these nodes.
 *
 * <p>
 * This class provides methods to add nodes to the network and connect nodes
 * with pipes. It also maintains a list of customers and logs actions performed
 * on the network.</p>
 *
 * <p>
 * Usage example:</p>
 * <pre>{@code
 * WaterSource source = new WaterSource(...);
 * WaterDistribution distribution = new WaterDistribution(source);
 * NetworkNode node1 = new NetworkNode(...);
 * NetworkNode node2 = new NetworkNode(...);
 * distribution.addNode(node1);
 * distribution.addNode(node2);
 * PipeProperties properties = new PipeProperties(...);
 * distribution.connectNodes(node1, node2, properties);
 * }</pre>
 *
 * <p>
 * Note: The source node is automatically added to the network upon
 * instantiation.</p>
 *
 */
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

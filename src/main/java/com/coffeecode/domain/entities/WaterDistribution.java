package com.coffeecode.domain.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.coffeecode.domain.values.pipe.PipeProperties;
import com.coffeecode.validation.exceptions.ValidationException;
import com.coffeecode.validation.validators.WaterDistributionValidation;

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
public final class WaterDistribution {

    private final Map<UUID, NetworkNode> nodes = new HashMap<>();
    private final Map<UUID, Pipe> pipes = new HashMap<>();
    private final List<WaterCustomer> customers = new ArrayList<>();
    private final WaterSource source;

    private WaterDistribution(WaterDistributionBuilder builder) {
        WaterDistributionValidation.validateSource(builder.source);
        this.source = builder.source;
    }

    public static WaterDistributionBuilder builder() {
        return new WaterDistributionBuilder();
    }

    public synchronized void addNode(NetworkNode node) {
        WaterDistributionValidation.validateNode(node);
        nodes.put(node.getId(), node);
        if (node instanceof WaterCustomer customer) {
            customers.add(customer);
        }
        log.info("Added node: {}", node);
    }

    public synchronized Optional<Pipe> connectNodes(NetworkNode from, NetworkNode to, PipeProperties properties) {
        try {
            WaterDistributionValidation.validateConnection(from, to, properties);
            validateNodesPresent(from, to);

            Pipe pipe = Pipe.builder()
                    .source(from)
                    .destination(to)
                    .properties(properties)
                    .build();

            pipes.put(pipe.getId(), pipe);
            log.info("Connected nodes with pipe: {}", pipe);
            return Optional.of(pipe);
        } catch (ValidationException e) {
            log.error("Failed to connect nodes: {}", e.getMessage());
            return Optional.empty();
        }
    }

    private void validateNodesPresent(NetworkNode from, NetworkNode to) {
        if (!nodes.containsKey(from.getId())) {
            throw new ValidationException("Source node not in network");
        }
        if (!nodes.containsKey(to.getId())) {
            throw new ValidationException("Destination node not in network");
        }
    }

    public static final class WaterDistributionBuilder {

        private WaterSource source;

        private WaterDistributionBuilder() {
        }

        public WaterDistributionBuilder source(WaterSource source) {
            this.source = source;
            return this;
        }

        public WaterDistribution build() {
            WaterDistribution distribution = new WaterDistribution(this);
            distribution.addNode(distribution.source);
            return distribution;
        }
    }

    // Immutable view getters
    public Map<UUID, NetworkNode> getNodes() {
        return Collections.unmodifiableMap(nodes);
    }

    public Map<UUID, Pipe> getPipes() {
        return Collections.unmodifiableMap(pipes);
    }

    public List<WaterCustomer> getCustomers() {
        return Collections.unmodifiableList(customers);
    }
}

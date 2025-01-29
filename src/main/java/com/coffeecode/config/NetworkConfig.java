package com.coffeecode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.coffeecode.domain.factory.node.DefaultNodeFactory;
import com.coffeecode.domain.factory.node.NodeFactory;
import com.coffeecode.domain.factory.edge.DefaultEdgeFactory;
import com.coffeecode.domain.factory.edge.EdgeFactory;
import com.coffeecode.domain.common.connection.ConnectionManager;
import com.coffeecode.domain.node.base.Node;
import com.coffeecode.domain.node.base.NodeType;
import com.coffeecode.domain.node.properties.ClientNodeProperties;
import com.coffeecode.domain.node.properties.RouterNodeProperties;
import com.coffeecode.domain.node.properties.ServerNodeProperties;

@Configuration
public class NetworkConfig {

    @Bean
    public NodeFactory nodeFactory() {
        return new DefaultNodeFactory();
    }

    @Bean
    public EdgeFactory edgeFactory() {
        return new DefaultEdgeFactory();
    }

    @Bean
    @Scope("prototype")
    public RouterNodeProperties defaultRouterProperties() {
        return RouterNodeProperties.builder()
                .routingCapacity(NodeType.ROUTER.getDefaultCapacity())
                .bufferSize(1024.0)
                .supportsQos(true)
                .build();
    }

    @Bean
    @Scope("prototype")
    public ClientNodeProperties defaultClientProperties() {
        return ClientNodeProperties.builder()
                .dataRate(100.0)
                .maxBandwidth((double) NodeType.CLIENT.getDefaultCapacity())
                .build();
    }

    @Bean
    @Scope("prototype")
    public ServerNodeProperties defaultServerProperties() {
        return ServerNodeProperties.builder()
                .capacity(NodeType.SERVER.getDefaultCapacity())
                .processingPower(1000.0)
                .maxConnections(100)
                .build();
    }

    @Bean
    @Scope("prototype")
    public ConnectionManager connectionManager(Node owner) {
        return new ConnectionManager(owner);
    }
}

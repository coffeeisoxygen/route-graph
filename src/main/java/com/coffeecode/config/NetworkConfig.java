package com.coffeecode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.coffeecode.domain.common.NetNodeType;
import com.coffeecode.domain.connection.ConnectionManager;
import com.coffeecode.domain.connection.DefaultConnectionManager;
import com.coffeecode.domain.factory.edge.DefaultEdgeFactory;
import com.coffeecode.domain.factory.edge.NetEdgeFactory;
import com.coffeecode.domain.factory.node.DefaultNodeFactory;
import com.coffeecode.domain.factory.node.NetNodeFactory;
import com.coffeecode.domain.node.properties.ClientNodeProperties;
import com.coffeecode.domain.node.properties.RouterNodeProperties;
import com.coffeecode.domain.node.properties.ServerNodeProperties;

@Configuration
public class NetworkConfig {

    @Bean
    public NetNodeFactory nodeFactory(ConnectionManager connectionManager) {
        return new DefaultNodeFactory(connectionManager);
    }

    @Bean
    public NetEdgeFactory edgeFactory() {
        return new DefaultEdgeFactory();
    }

    @Bean
    @Scope("prototype")
    public RouterNodeProperties defaultRouterProperties() {
        return RouterNodeProperties.builder()
                .routingCapacity(NetNodeType.ROUTER.getDefaultCapacity())
                .bufferSize(1024.0)
                .supportsQos(true)
                .build();
    }

    @Bean
    @Scope("prototype")
    public ClientNodeProperties defaultClientProperties() {
        return ClientNodeProperties.builder()
                .dataRate(100.0)
                .maxBandwidth((double) NetNodeType.CLIENT.getDefaultCapacity())
                .build();
    }

    @Bean
    @Scope("prototype")
    public ServerNodeProperties defaultServerProperties() {
        return ServerNodeProperties.builder()
                .capacity(NetNodeType.SERVER.getDefaultCapacity())
                .processingPower(1000.0)
                .maxConnections(100)
                .build();
    }

    @Bean
    @Scope("prototype")
    public ConnectionManager connectionManager() {
        return new DefaultConnectionManager(null);
    }
}

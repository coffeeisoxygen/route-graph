package com.coffeecode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.coffeecode.infrastructure.monitoring.NetworkMonitor;
import com.coffeecode.infrastructure.validation.NetworkValidator;
import com.coffeecode.service.routing.DijkstraRoutingStrategy;


@Configuration
public class NetworkConfig {

    @Bean
    public NetworkValidator networkValidator() {
        return new NetworkValidator();
    }

    @Bean
    public NetworkMonitor networkMonitor() {
        return new NetworkMonitor();
    }

    @Bean
    public RoutingService routingService() {
        return new DijkstraRoutingStrategy();
    }
}

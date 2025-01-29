package com.coffeecode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.coffeecode.domain.connection.ConnectionManager;
import com.coffeecode.domain.connection.DefaultConnectionManager;

@Configuration
public class TestConfig {
    @Bean
    public ConnectionManager connectionManager() {
        return new DefaultConnectionManager(null);
    }
}

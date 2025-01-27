package com.coffeecode.config;

import java.io.IOException;
import java.util.Properties;

import com.coffeecode.exception.AppException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppProperties {
    private static final Properties props = new Properties();
    private static AppProperties instance;

    private AppProperties() {
        loadProperties();
    }

    private void loadProperties() {
        try {
            props.load(AppProperties.class
                    .getClassLoader()
                    .getResourceAsStream("application.properties"));
        } catch (IOException e) {
            log.error("Failed to load properties file", e);
            throw new AppException("Configuration could not be loaded!", e);
        }
    }

    public static AppProperties getInstance() {
        if (instance == null) {
            instance = new AppProperties();
        }
        return instance;
    }

    public double getDouble(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Property not found: " + key);
        }
        return Double.parseDouble(value);
    }
}

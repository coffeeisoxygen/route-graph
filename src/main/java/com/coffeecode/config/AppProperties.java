package com.coffeecode.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppProperties {
    private static final Properties props = new Properties();

    protected AppProperties() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IllegalStateException("Cannot find application.properties");
            }
            props.load(input);
        } catch (IOException ex) {
            log.error("Error loading properties", ex);
            throw new IllegalStateException("Failed to load properties", ex);
        }
    }

    public double getDouble(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Property not found: " + key);
        }
        return Double.parseDouble(value);
    }

    public static AppProperties getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final AppProperties INSTANCE = new AppProperties();
    }
}

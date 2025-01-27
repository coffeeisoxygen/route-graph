package com.coffeecode.config;

import java.io.IOException;
import java.io.InputStream;
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
        try (InputStream input = AppProperties.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new AppException("Unable to find application.properties");
            }
            props.load(input);
        } catch (IOException ex) {
            throw new AppException("Error loading properties", ex);
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

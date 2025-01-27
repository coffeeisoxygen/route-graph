package com.coffeecode.model;

import com.coffeecode.model.coordinates.CartesianCoordinates;
import com.coffeecode.model.coordinates.GeographicCoordinates;

public class NodeFactory {
    public static Node createCartesianNode(double x, double y, double elevation,
            double velocity, double pressure) {
        return Node.builder()
                .coordinates(new CartesianCoordinates(x, y))
                .elevation(elevation)
                .velocity(velocity)
                .pressure(pressure)
                .build();
    }

    public static Node createGeographicNode(double latitude, double longitude,
            double elevation, double velocity,
            double pressure) {
        return Node.builder()
                .coordinates(new GeographicCoordinates(latitude, longitude))
                .elevation(elevation)
                .velocity(velocity)
                .pressure(pressure)
                .build();
    }

    private NodeFactory() {
        // Prevent instantiation
    }
}

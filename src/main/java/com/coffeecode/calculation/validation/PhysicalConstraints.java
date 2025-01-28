package com.coffeecode.calculation.validation;

public interface PhysicalConstraints {
    // Pipe constraints
    double MIN_PIPE_DIAMETER = 0.05; // meters
    double MAX_PIPE_DIAMETER = 2.0; // meters

    // Node constraints
    double MIN_VELOCITY = 0.0; // m/s
    double MAX_VELOCITY = 3.0; // m/s
    double MIN_PRESSURE = 0.0; // Pa
    double MAX_PRESSURE = 1e6; // Pa (1 MPa)
    double MIN_ELEVATION = -500.0; // meters (Dead Sea)
    double MAX_ELEVATION = 8848.0; // meters (Mt. Everest)
}

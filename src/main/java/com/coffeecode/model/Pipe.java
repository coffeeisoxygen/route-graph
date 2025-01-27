package com.coffeecode.model;

import lombok.Value;

@Value
public class Pipe {
    Node start;
    Node end;
    double length; // meters
    double diameter; // meters
    double roughness; // meters

    // Common pipe roughness values
    public static final double PVC_ROUGHNESS = 0.0015e-3; // m
    public static final double STEEL_ROUGHNESS = 0.045e-3; // m
}

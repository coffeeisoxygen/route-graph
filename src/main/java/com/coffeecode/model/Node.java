package com.coffeecode.model;

import lombok.Value;

@Value
public class Node {
    double x; // coordinate x
    double y; // coordinate y
    double elevation; // meters above sea level
    double pressure; // Pascal (Pa)
}

package com.coffeecode.domain.model;

import java.util.UUID;

import lombok.Value;

@Value
public class NetworkIdentity {
    UUID id;
    String name;
    NodeType type;

}

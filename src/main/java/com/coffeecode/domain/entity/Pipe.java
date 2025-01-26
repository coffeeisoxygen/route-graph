package com.coffeecode.domain.entity;

import java.util.UUID;

import com.coffeecode.domain.objects.PipeProperties;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Pipe {

    private final UUID id;
    private final NetworkNode source;
    private final NetworkNode destination;
    private final PipeProperties properties;

    public Pipe(NetworkNode source, NetworkNode destination, PipeProperties properties) {
        this.id = UUID.randomUUID();
        this.source = source;
        this.destination = destination;
        this.properties = properties;
    }
}

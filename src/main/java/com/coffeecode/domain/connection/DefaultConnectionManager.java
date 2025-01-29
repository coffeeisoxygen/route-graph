package com.coffeecode.domain.connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.coffeecode.domain.model.NetworkEdge;
import com.coffeecode.domain.model.NetworkNode;
import com.coffeecode.domain.validation.NetworkValidator;
import com.coffeecode.domain.validation.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Scope("prototype")
public class DefaultConnectionManager implements ConnectionManager {
    private final List<NetworkEdge> connections;
    private final NetworkNode owner;
    private final ReadWriteLock lock;
    private final NetworkValidator validator;

    public DefaultConnectionManager(NetworkNode owner, NetworkValidator validator) {
        this.owner = owner;
        this.validator = validator;
        this.connections = new ArrayList<>();
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public List<NetworkEdge> getConnections() {
        lock.readLock().lock();
        try {
            return Collections.unmodifiableList(new ArrayList<>(connections));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void addConnection(NetworkEdge edge) {
        lock.writeLock().lock();
        try {
            var result = validator.validateConnection(owner, edge.getDestination(), edge);
            if (result.hasErrors()) {
                throw new ValidationException(String.join(", ", result.getErrors()));
            }
            if (connections.contains(edge)) {
                throw new ValidationException("Connection already exists");
            }
            connections.add(edge);
            log.debug("Added connection from {} to {}",
                    owner.getIdentity().getName(),
                    edge.getDestination().getIdentity().getName());
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void removeConnection(NetworkEdge edge) {
        lock.writeLock().lock();
        try {
            connections.remove(edge);
            log.debug("Removed connection from {} to {}",
                    owner.getIdentity().getName(),
                    edge.getDestination().getIdentity().getName());
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean hasConnection(NetworkEdge edge) {
        lock.readLock().lock();
        try {
            return connections.contains(edge);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Optional<NetworkEdge> findConnection(NetworkNode target) {
        lock.readLock().lock();
        try {
            return connections.stream()
                    .filter(edge -> edge.getDestination().equals(target))
                    .findFirst();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public int getConnectionCount() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getConnectionCount'");
    }

    @Override
    public boolean isConnectedTo(NetworkNode target) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isConnectedTo'");
    }

}

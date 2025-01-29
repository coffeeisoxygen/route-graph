package com.coffeecode.domain.node.base;

import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.connection.ConnectionManager;
import com.coffeecode.domain.edge.Edge;

@ExtendWith(MockitoExtension.class)
public abstract class BaseNodeTest {
    @Mock
    protected ConnectionManager connectionManager;

    @Mock
    protected Edge mockEdge;

    @BeforeEach
    void setUp() {
        lenient().when(connectionManager.getConnectionCount()).thenReturn(0);
        lenient().when(mockEdge.isValid()).thenReturn(true);
    }
}

package com.coffeecode.domain.factory.node;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.connection.ConnectionManager;
import com.coffeecode.domain.node.base.Node;
import com.coffeecode.domain.node.base.NodeType;
import com.coffeecode.domain.node.properties.RouterNodeProperties;

@ExtendWith(MockitoExtension.class)
class DefaultNodeFactoryTest {
    @Mock private ConnectionManager connectionManager;
    private DefaultNodeFactory factory;

    @BeforeEach
    void setUp() {
        factory = new DefaultNodeFactory(connectionManager);
    }

    @Test
    void shouldCreateRouter() {
        RouterNodeProperties props = RouterNodeProperties.builder()
            .routingCapacity(1000)
            .bufferSize(1024.0)
            .build();

        Node router = factory.createRouter(props);

        assertNotNull(router);
        assertEquals(NodeType.ROUTER, router.getType());
    }

    @Test
    void shouldCreateBatchNodes() {
        RouterNodeProperties props = RouterNodeProperties.builder()
            .routingCapacity(1000)
            .bufferSize(1024.0)
            .build();

        List<Node> nodes = factory.createBatch(NodeType.ROUTER, 3, props);

        assertEquals(3, nodes.size());
        nodes.forEach(node -> assertEquals(NodeType.ROUTER, node.getType()));
    }

    @Test
    void shouldValidateProperties() {
        assertThrows(IllegalArgumentException.class,
            () -> factory.createRouter(null));
    }
}

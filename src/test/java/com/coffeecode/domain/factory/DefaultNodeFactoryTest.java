package com.coffeecode.domain.factory;

import com.coffeecode.domain.connection.ConnectionManager;
import com.coffeecode.domain.factory.node.DefaultNodeFactory;
import com.coffeecode.domain.node.base.Node;
import com.coffeecode.domain.node.base.NodeType;
import com.coffeecode.domain.node.properties.RouterNodeProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DefaultNodeFactoryTest {

    @Mock
    private ConnectionManager connectionManager;

    private DefaultNodeFactory factory;
    private RouterNodeProperties routerProps;

    @BeforeEach
    void setUp() {
        factory = new DefaultNodeFactory(connectionManager);
        routerProps = RouterNodeProperties.builder()
                .routingCapacity(10)
                .bufferSize(1000.0)
                .supportsQos(true)
                .build();
    }

    @Test
    void shouldCreateSingleNode() {
        Node router = factory.createRouter(routerProps);
        assertNotNull(router);
        assertEquals(NodeType.ROUTER, router.getType());
    }

    @Test
    void shouldCreateBatchNodes() {
        List<Node> nodes = factory.createBatch(NodeType.ROUTER, 3, routerProps);
        assertEquals(3, nodes.size());
        assertTrue(nodes.stream().allMatch(n -> n.getType() == NodeType.ROUTER));
    }

    @Test
    void shouldValidateProperties() {
        assertThrows(IllegalArgumentException.class,
            () -> factory.createRouter(null));
    }

    @Test
    void shouldValidateBatchParameters() {
        assertThrows(IllegalArgumentException.class,
            () -> factory.createBatch(null, 1, routerProps));

        assertThrows(IllegalArgumentException.class,
            () -> factory.createBatch(NodeType.ROUTER, 0, routerProps));

        assertThrows(IllegalArgumentException.class,
            () -> factory.createBatch(NodeType.ROUTER, 1, null));
    }
}

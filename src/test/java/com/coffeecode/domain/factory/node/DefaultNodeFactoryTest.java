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

import com.coffeecode.domain.common.NetNodeType;
import com.coffeecode.domain.connection.ConnectionManager;
import com.coffeecode.domain.node.base.NetNode;
import com.coffeecode.domain.node.properties.RouterNodeProperties;

@ExtendWith(MockitoExtension.class)
class DefaultNodeFactoryTest {
    @Mock
    private ConnectionManager connectionManager;
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

        NetNode router = factory.createRouter(props);

        assertNotNull(router);
        assertEquals(NetNodeType.ROUTER, router.getType());
    }

    @Test
    void shouldCreateBatchNodes() {
        RouterNodeProperties props = RouterNodeProperties.builder()
                .routingCapacity(1000)
                .bufferSize(1024.0)
                .build();

        List<NetNode> nodes = factory.createBatch(NetNodeType.ROUTER, 3, props);

        assertEquals(3, nodes.size());
        nodes.forEach(node -> assertEquals(NetNodeType.ROUTER, node.getType()));
    }

    @Test
    void shouldValidateProperties() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.createRouter(null));
    }
}

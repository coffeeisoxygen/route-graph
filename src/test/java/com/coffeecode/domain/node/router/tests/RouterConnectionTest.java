package com.coffeecode.domain.node.router.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.node.router.base.RouterNodeTest;

@DisplayName("Router Connection Tests")
class RouterConnectionTest extends RouterNodeTest {

    @Test
    @DisplayName("Should respect max connections")
    void shouldRespectMaxConnections() {
        for (int i = 0; i < properties.getMaxConnections(); i++) {
            boolean added = routerNode.addConnection(createValidEdge());
            assertThat(added).isTrue();
        }

        boolean exceeded = routerNode.addConnection(createValidEdge());
        assertThat(exceeded).isFalse();
        assertThat(routerNode.getComponents().getConnections().getConnectionCount())
                .isEqualTo(properties.getMaxConnections());
    }

    @Test
    @DisplayName("Should reject connections when inactive")
    void shouldRejectConnectionsWhenInactive() {
        routerNode.setActive(false);
        assertThat(routerNode.addConnection(createValidEdge())).isFalse();
    }
}

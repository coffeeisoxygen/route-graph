package com.coffeecode.domain.node.router.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.model.NodeType;
import com.coffeecode.domain.node.router.base.RouterNodeTest;

@DisplayName("Router Component Tests")
class RouterComponentTest extends RouterNodeTest {

    @Test
    @DisplayName("Should initialize all components")
    void shouldInitializeComponents() {
        routerNode.setActive(true);

        assertThat(routerNode.getComponents().isActive()).isTrue();
        assertThat(routerNode.isActive()).isTrue();
    }

    @Test
    @DisplayName("Should clear components on deactivate")
    void shouldClearComponentsOnDeactivate() {
        NetworkIdentity destination = NetworkIdentity.create(NodeType.SERVER);
        NetworkIdentity nextHop = NetworkIdentity.create(NodeType.ROUTER);

        routerNode.updateRoute(destination, nextHop, 10.0);
        routerNode.setActive(false);

        assertThat(routerNode.getComponents().isActive()).isFalse();
        assertThat(routerNode.findRoute(destination)).isEmpty();
    }
}

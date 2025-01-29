package com.coffeecode.domain.node.router.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.node.router.RouterNode;
import com.coffeecode.domain.node.router.base.RouterNodeTest;

@DisplayName("Router Basic Operations")
class RouterBasicTest extends RouterNodeTest {

    @Test
    @DisplayName("Should create router with valid properties")
    void shouldCreateWithValidProperties() {
        assertThat(routerNode.getProperties()).isEqualTo(properties);
        assertThat(routerNode.isActive()).isTrue();
    }

    @Test
    @DisplayName("Should throw on null properties")
    void shouldThrowOnNullProperties() {
        assertThatThrownBy(() -> RouterNode.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Properties cannot be null");
    }
}

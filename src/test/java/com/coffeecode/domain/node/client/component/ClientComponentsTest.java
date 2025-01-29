package com.coffeecode.domain.node.client.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("ClientComponents Tests")
class ClientComponentsTest {
    private ConnectionManager mockConnections;
    private RequestManager mockRequests;
    private ClientComponents components;

    @BeforeEach
    void setUp() {
        mockConnections = mock(ConnectionManager.class);
        mockRequests = mock(RequestManager.class);

        components = ClientComponents.builder()
                .connections(mockConnections)
                .requests(mockRequests)
                .build();
    }

    @Nested
    @DisplayName("Lifecycle Management")
    class LifecycleTests {
        @Test
        @DisplayName("Should initialize all components")
        void shouldInitializeComponents() {
            // When
            components.initialize();

            // Then
            verify(mockConnections).initialize();
            verify(mockRequests).initialize();
        }

        @Test
        @DisplayName("Should clear all components")
        void shouldClearComponents() {
            // When
            components.clear();

            // Then
            verify(mockConnections).clear();
            verify(mockRequests).clear();
        }
    }

    @Nested
    @DisplayName("State Management")
    class StateTests {
        @Test
        @DisplayName("Should be active when all components are active")
        void shouldBeActiveWhenAllComponentsActive() {
            // Given
            when(mockConnections.isActive()).thenReturn(true);
            when(mockRequests.isActive()).thenReturn(true);

            // Then
            assertThat(components.isActive()).isTrue();
        }

        @Test
        @DisplayName("Should be inactive when any component is inactive")
        void shouldBeInactiveWhenAnyComponentInactive() {
            // Given
            when(mockConnections.isActive()).thenReturn(true);
            when(mockRequests.isActive()).thenReturn(false);

            // Then
            assertThat(components.isActive()).isFalse();
        }
    }

    @Test
    @DisplayName("Should provide access to components")
    void shouldProvideAccessToComponents() {
        assertThat(components.getConnections()).isSameAs(mockConnections);
        assertThat(components.getRequests()).isSameAs(mockRequests);
    }
}

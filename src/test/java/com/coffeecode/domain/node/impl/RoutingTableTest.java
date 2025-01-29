package com.coffeecode.domain.node.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.node.router.component.RoutingTable;
import com.coffeecode.domain.node.router.model.RouteInfo;

public class RoutingTableTest {

    private RoutingTable routingTable;
    private NetworkIdentity destination;
    private RouteInfo routeInfo;

    @BeforeEach
    public void setUp() {
        routingTable = new RoutingTable();
        destination = mock(NetworkIdentity.class);
        routeInfo = mock(RouteInfo.class);
    }

    @Test
    public void testUpdateRoute_AddNewRoute() {
        Optional<RouteInfo> previousRoute = routingTable.updateRoute(destination, routeInfo);
        assertThat(previousRoute).isEmpty();
        assertThat(routingTable.getRoute(destination)).contains(routeInfo);
    }

    @Test
    public void testUpdateRoute_UpdateExistingRoute() {
        routingTable.updateRoute(destination, routeInfo);
        RouteInfo newRouteInfo = mock(RouteInfo.class);
        Optional<RouteInfo> previousRoute = routingTable.updateRoute(destination, newRouteInfo);
        assertThat(previousRoute).contains(routeInfo);
        assertThat(routingTable.getRoute(destination)).contains(newRouteInfo);
    }

    @Test
    public void testGetRoute_RouteExists() {
        routingTable.updateRoute(destination, routeInfo);
        Optional<RouteInfo> retrievedRoute = routingTable.getRoute(destination);
        assertThat(retrievedRoute).contains(routeInfo);
    }

    @Test
    public void testGetRoute_RouteDoesNotExist() {
        Optional<RouteInfo> retrievedRoute = routingTable.getRoute(destination);
        assertThat(retrievedRoute).isEmpty();
    }

    @Test
    public void testRemoveRoute_RouteExists() {
        routingTable.updateRoute(destination, routeInfo);
        Optional<RouteInfo> removedRoute = routingTable.removeRoute(destination);
        assertThat(removedRoute).contains(routeInfo);
        assertThat(routingTable.getRoute(destination)).isEmpty();
    }

    @Test
    public void testRemoveRoute_RouteDoesNotExist() {
        Optional<RouteInfo> removedRoute = routingTable.removeRoute(destination);
        assertThat(removedRoute).isEmpty();
    }

    @Test
    public void testGetDestinations() {
        routingTable.updateRoute(destination, routeInfo);
        assertThat(routingTable.getDestinations()).contains(destination);
    }

    @Test
    public void testClear() {
        routingTable.updateRoute(destination, routeInfo);
        routingTable.clear();
        assertThat(routingTable.getRoute(destination)).isEmpty();
        assertThat(routingTable.getDestinations()).isEmpty();
    }

    @Test
    public void testGetRouteCount() {
        assertThat(routingTable.getRouteCount()).isEqualTo(0);
        routingTable.updateRoute(destination, routeInfo);
        assertThat(routingTable.getRouteCount()).isEqualTo(1);
    }

    @Test
    public void testUpdateRoute_NullDestination() {
        assertThrows(IllegalArgumentException.class, () -> routingTable.updateRoute(null, routeInfo));
    }

    @Test
    public void testUpdateRoute_NullRouteInfo() {
        assertThrows(IllegalArgumentException.class, () -> routingTable.updateRoute(destination, null));
    }
}

package com.coffeecode.domain.node.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.coffeecode.domain.model.NetworkIdentity;
import com.coffeecode.domain.node.router.model.RouteInfo;

public class RouteInfoTest {

    @Test
    public void testIsValid_withValidNextHopAndMetric_shouldReturnTrue() {
        NetworkIdentity mockNetworkIdentity = mock(NetworkIdentity.class);
        RouteInfo routeInfo = RouteInfo.builder()
                .nextHop(mockNetworkIdentity)
                .metric(10.0)
                .timestamp(System.currentTimeMillis())
                .build();

        assertThat(routeInfo.isValid()).isTrue();
    }

    @Test
    public void testIsValid_withNullNextHop_shouldReturnFalse() {
        RouteInfo routeInfo = RouteInfo.builder()
                .nextHop(null)
                .metric(10.0)
                .timestamp(System.currentTimeMillis())
                .build();

        assertThat(routeInfo.isValid()).isFalse();
    }

    @Test
    public void testIsValid_withNegativeMetric_shouldReturnFalse() {
        NetworkIdentity mockNetworkIdentity = mock(NetworkIdentity.class);
        RouteInfo routeInfo = RouteInfo.builder()
                .nextHop(mockNetworkIdentity)
                .metric(-1.0)
                .timestamp(System.currentTimeMillis())
                .build();

        assertThat(routeInfo.isValid()).isFalse();
    }

    @Test
    public void testIsValid_withNullNextHopAndNegativeMetric_shouldReturnFalse() {
        RouteInfo routeInfo = RouteInfo.builder()
                .nextHop(null)
                .metric(-1.0)
                .timestamp(System.currentTimeMillis())
                .build();

        assertThat(routeInfo.isValid()).isFalse();
    }
}

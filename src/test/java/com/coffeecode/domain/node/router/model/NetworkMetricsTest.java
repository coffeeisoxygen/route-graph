// package com.coffeecode.domain.node.router.model;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.assertj.core.api.Assertions.assertThatThrownBy;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Nested;
// import org.junit.jupiter.api.Test;

// class NetworkMetricsTest {
// private NetworkMetrics metrics;

// @BeforeEach
// void setUp() {
// metrics = new NetworkMetrics();
// }

// @Nested
// @DisplayName("Basic Metric Operations")
// class BasicMetricTests {
// @Test
// @DisplayName("Should store and retrieve metrics")
// void shouldStoreAndRetrieveMetrics() {
// // Given
// double value = 20.0;

// // When
// metrics.updateMetric(value);

// // Then
// MetricsSnapshot snapshot = metrics.getMetrics();
// assertThat(snapshot.getAverageValue()).isEqualTo(value);
// assertThat(snapshot.getSampleCount()).isEqualTo(1);
// }

// @Test
// @DisplayName("Should calculate average correctly")
// void shouldCalculateAverageCorrectly() {
// // Given
// double[] values = { 10.0, 20.0, 30.0 };
// double expectedAverage = 20.0;

// // When
// for (double value : values) {
// metrics.updateMetric(value);
// }

// // Then
// MetricsSnapshot snapshot = metrics.getMetrics();
// assertThat(snapshot.getAverageValue()).isEqualTo(expectedAverage);
// assertThat(snapshot.getSampleCount()).isEqualTo(3);
// }
// }

// @Nested
// @DisplayName("Window Management")
// class WindowTests {
// @Test
// @DisplayName("Should maintain window size")
// void shouldMaintainWindowSize() {
// // Given
// int windowSize = 10;

// // When
// for (int i = 0; i < windowSize + 5; i++) {
// metrics.updateMetric(i);
// }

// // Then
// MetricsSnapshot snapshot = metrics.getMetrics();
// assertThat(snapshot.getSampleCount()).isEqualTo(windowSize);
// }
// }

// @Nested
// @DisplayName("Validation")
// class ValidationTests {
// @Test
// @DisplayName("Should reject invalid metrics")
// void shouldRejectInvalidMetrics() {
// assertThatThrownBy(() -> metrics.updateMetric(Double.NaN))
// .isInstanceOf(IllegalArgumentException.class);

// assertThatThrownBy(() -> metrics.updateMetric(Double.POSITIVE_INFINITY))
// .isInstanceOf(IllegalArgumentException.class);
// }
// }
// }

package com.coffeecode.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.entities.Customer;
import com.coffeecode.domain.entities.Pipe;
import com.coffeecode.domain.entities.WaterDistribution;
import com.coffeecode.domain.entities.WaterSource;
import com.coffeecode.domain.objects.WaterDemand;
import com.coffeecode.domain.values.Coordinate;
import com.coffeecode.domain.values.Distance;
import com.coffeecode.domain.values.PipeProperties;
import com.coffeecode.domain.values.Volume;
import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("WaterDistribution Tests")
class WaterDistributionTest {

    private WaterSource source;
    private Customer customer;
    private PipeProperties properties;

    @BeforeEach
    void setUp() {
        source = WaterSource.builder()
                .name("MainSource")
                .location(Coordinate.of(0.0, 0.0))
                .capacity(Volume.of(1000.0))
                .build();

        customer = Customer.builder()
                .name("Customer1")
                .location(Coordinate.of(1.0, 1.0))
                .waterDemand(WaterDemand.of(Volume.of(100.0)))
                .build();

        properties = PipeProperties.of(
                Distance.of(100.0),
                Volume.of(500.0)
        );
    }

    @Test
    @DisplayName("Should create network with valid source")
    void shouldCreateNetworkWithValidSource() {
        WaterDistribution network = WaterDistribution.builder()
                .source(source)
                .build();

        assertNotNull(network);
        assertEquals(source, network.getSource());
        assertTrue(network.getNodes().containsKey(source.getId()));
        assertTrue(network.getPipes().isEmpty());
        assertTrue(network.getCustomers().isEmpty());
    }

    @Test
    @DisplayName("Should throw ValidationException for null source")
    void shouldThrowValidationExceptionForNullSource() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> WaterDistribution.builder().build()
        );
        assertTrue(exception.getMessage().contains("Source cannot be null"));
    }

    @Test
    @DisplayName("Should add customer node successfully")
    void shouldAddCustomerNodeSuccessfully() {
        WaterDistribution network = WaterDistribution.builder()
                .source(source)
                .build();

        network.addNode(customer);

        assertTrue(network.getNodes().containsKey(customer.getId()));
        assertTrue(network.getCustomers().contains(customer));
    }

    @Test
    @DisplayName("Should connect nodes successfully")
    void shouldConnectNodesSuccessfully() {
        WaterDistribution network = WaterDistribution.builder()
                .source(source)
                .build();
        network.addNode(customer);

        Optional<Pipe> result = network.connectNodes(source, customer, properties);

        assertTrue(result.isPresent());
        assertEquals(source, result.get().getSource());
        assertEquals(customer, result.get().getDestination());
        assertTrue(network.getPipes().containsKey(result.get().getId()));
    }

    @Test
    @DisplayName("Should not connect nodes when source not in network")
    void shouldNotConnectNodesWhenSourceNotInNetwork() {
        WaterDistribution network = WaterDistribution.builder()
                .source(source)
                .build();

        WaterSource otherSource = WaterSource.builder()
                .name("OtherSource")
                .location(Coordinate.of(2.0, 2.0))
                .capacity(Volume.of(1000.0))
                .build();

        Optional<Pipe> result = network.connectNodes(otherSource, customer, properties);

        assertTrue(result.isEmpty());
    }
}

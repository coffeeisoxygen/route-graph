package com.coffeecode.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.entities.Customer;
import com.coffeecode.domain.entities.NodeType;
import com.coffeecode.domain.objects.WaterDemand;
import com.coffeecode.domain.values.Coordinate;
import com.coffeecode.domain.values.Volume;
import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("Customer Entity Tests")
class CustomerTest {

    private Coordinate location;
    private WaterDemand demand;

    @BeforeEach
    void setUp() {
        location = Coordinate.of(0.0, 0.0);
        demand = WaterDemand.of(Volume.of(100.0));
    }

    @Test
    @DisplayName("Should create customer with valid parameters")
    void shouldCreateCustomerWithValidParameters() {
        Customer customer = Customer.builder()
            .name("Customer1")
            .location(location)
            .waterDemand(demand)
            .build();

        assertNotNull(customer.getId());
        assertEquals("Customer1", customer.getName());
        assertEquals(location, customer.getLocation());
        assertEquals(NodeType.CUSTOMER, customer.getType());
        assertEquals(NodeType.CUSTOMER, customer.getType());
    }

    @Test
    @DisplayName("Should generate unique IDs for different customers")
    void shouldGenerateUniqueIds() {
        Customer customer1 = Customer.builder()
            .name("Customer1")
            .location(location)
            .waterDemand(demand)
            .build();
        Customer customer2 = Customer.builder()
            .name("Customer2")
            .location(location)
            .waterDemand(demand)
            .build();

        assertNotEquals(customer1.getId(), customer2.getId());
    }

    @Test
    @DisplayName("Should throw exception for null name")
    void shouldThrowExceptionForNullName() {
        ValidationException exception = assertThrows(ValidationException.class,
            () -> Customer.builder()
                .name(null)
                .location(location)
                .waterDemand(demand)
                .build());
        assertTrue(exception.getMessage().contains("Customer name cannot be empty"));
    }

    @Test
    @DisplayName("Should throw exception for null location")
    void shouldThrowExceptionForNullLocation() {
        ValidationException exception = assertThrows(ValidationException.class,
            () -> Customer.builder()
                .name("Customer1")
                .location(null)
                .waterDemand(demand)
                .build());
        assertTrue(exception.getMessage().contains("Location cannot be null"));
    }

    @Test
    @DisplayName("Should throw exception for null water demand")
    void shouldThrowExceptionForNullWaterDemand() {
        ValidationException exception = assertThrows(ValidationException.class,
            () -> Customer.builder()
                .name("Customer1")
                .location(location)
                .waterDemand(null)
                .build());
        assertTrue(exception.getMessage().contains("Water demand cannot be null"));
    }

    @Test
    @DisplayName("Should implement toString correctly")
    void shouldImplementToString() {
        Customer customer = Customer.builder()
            .name("Customer1")
            .location(location)
            .waterDemand(demand)
            .build();
        String toString = customer.toString();

        assertTrue(toString.contains("Customer1"));
        assertTrue(toString.contains("CUSTOMER"));
        assertTrue(toString.contains(location.toString()));
    }
}

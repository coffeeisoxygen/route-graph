package com.coffeecode.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.objects.Coordinate;
import com.coffeecode.domain.objects.Volume;
import com.coffeecode.domain.objects.WaterDemand;
import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("Customer Tests")
class CustomerTest {

    private Coordinate location;
    private WaterDemand waterDemand;

    @BeforeEach
    void setUp() {
        location = Coordinate.of(0.0, 0.0);
        waterDemand = WaterDemand.of(Volume.of(100.0));
    }

    @Test
    @DisplayName("Should create customer with valid parameters")
    void shouldCreateCustomerWithValidParameters() {
        Customer customer = Customer.builder()
                .name("Customer1")
                .location(location)
                .waterDemand(waterDemand)
                .build();

        assertNotNull(customer.getId());
        assertEquals("Customer1", customer.getName());
        assertEquals(location, customer.getLocation());
        assertEquals(waterDemand, customer.getWaterDemand());
        assertEquals(NodeType.CUSTOMER, customer.getType());
    }

    @Test
    @DisplayName("Should throw ValidationException for null name")
    void shouldThrowValidationExceptionForNullName() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            Customer.builder()
                    .location(location)
                    .waterDemand(waterDemand)
                    .build();
        });
        assertTrue(exception.getMessage().contains("Customer name cannot be empty"));
    }

    @Test
    @DisplayName("Should throw ValidationException for empty name")
    void shouldThrowValidationExceptionForEmptyName() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            Customer.builder()
                    .name("  ")
                    .location(location)
                    .waterDemand(waterDemand)
                    .build();
        });
        assertTrue(exception.getMessage().contains("Customer name cannot be empty"));
    }

    @Test
    @DisplayName("Should throw ValidationException for null water demand")
    void shouldThrowValidationExceptionForNullWaterDemand() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            Customer.builder()
                    .name("Customer1")
                    .location(location)
                    .build();
        });
        assertTrue(exception.getMessage().contains("Water demand cannot be null"));
    }

    @Test
    @DisplayName("Should throw ValidationException for null location")
    void shouldThrowValidationExceptionForNullLocation() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            Customer.builder()
                    .name("Customer1")
                    .waterDemand(waterDemand)
                    .build();
        });
        assertTrue(exception.getMessage().contains("Location cannot be null"));
    }

    @Test
    @DisplayName("Should have unique IDs")
    void shouldHaveUniqueIds() {
        Customer customer1 = Customer.builder()
                .name("Customer1")
                .location(location)
                .waterDemand(waterDemand)
                .build();

        Customer customer2 = Customer.builder()
                .name("Customer1")
                .location(location)
                .waterDemand(waterDemand)
                .build();

        assertNotEquals(customer1.getId(), customer2.getId());
    }
}

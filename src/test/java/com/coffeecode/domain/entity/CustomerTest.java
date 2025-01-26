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
        Customer customer = new Customer("Customer1", location, waterDemand);

        assertNotNull(customer.getId());
        assertEquals("Customer1", customer.getName());
        assertEquals(location, customer.getLocation());
        assertEquals(waterDemand, customer.getWaterDemand());
        assertEquals(NetworkNode.NodeType.CUSTOMER, customer.getType());
    }

    @Test
    @DisplayName("Should throw ValidationException for null name")
    void shouldThrowValidationExceptionForNullName() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> new Customer(null, location, waterDemand)
        );
        assertTrue(exception.getMessage().contains("Customer name cannot be empty"));
    }

    @Test
    @DisplayName("Should throw ValidationException for empty name")
    void shouldThrowValidationExceptionForEmptyName() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> new Customer("  ", location, waterDemand)
        );
        assertTrue(exception.getMessage().contains("Customer name cannot be empty"));
    }

    @Test
    @DisplayName("Should throw ValidationException for null water demand")
    void shouldThrowValidationExceptionForNullWaterDemand() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> new Customer("Customer1", location, null)
        );
        assertTrue(exception.getMessage().contains("Water demand cannot be null"));
    }

    @Test
    @DisplayName("Should implement toString correctly")
    void shouldImplementToStringCorrectly() {
        Customer customer = new Customer("Customer1", location, waterDemand);
        String toString = customer.toString();

        assertTrue(toString.contains("Customer1"));
        assertTrue(toString.contains("CUSTOMER"));
        assertTrue(toString.contains(location.toString()));
    }

    @Test
    @DisplayName("Should have unique IDs for different instances")
    void shouldHaveUniqueIds() {
        Customer customer1 = new Customer("Customer1", location, waterDemand);
        Customer customer2 = new Customer("Customer1", location, waterDemand);

        assertNotEquals(customer1.getId(), customer2.getId());
    }

    @Test
    @DisplayName("Should implement equals and hashCode correctly")
    void shouldImplementEqualsAndHashCodeCorrectly() {
        Customer customer1 = new Customer("Customer1", location, waterDemand);
        Customer customer2 = new Customer("Customer1", location, waterDemand);
        Customer customer3 = new Customer("Customer2", location, waterDemand);

        // Different objects with same data should not be equal (due to UUID)
        assertNotEquals(customer1, customer2);
        assertNotEquals(customer1, customer3);

        // Same object should be equal to itself
        assertEquals(customer1, customer1);

        // Different types should not be equal
        assertNotEquals(customer1, "Customer1");
    }

    @Test
    @DisplayName("Should throw ValidationException for null location")
    void shouldThrowValidationExceptionForNullLocation() {
        ValidationException exception = assertThrows(
            ValidationException.class,
            () -> new Customer("Customer1", null, waterDemand)
        );
        assertTrue(exception.getMessage().contains("Location cannot be null"));
    }

    @Test
    @DisplayName("Should inherit NetworkNode properties")
    void shouldInheritNetworkNodeProperties() {
        Customer customer = new Customer("Customer1", location, waterDemand);

        // Verify inheritance
        assertTrue(customer instanceof NetworkNode);
        assertEquals(NetworkNode.NodeType.CUSTOMER, customer.getType());
        assertEquals(location, customer.getLocation());
    }
}

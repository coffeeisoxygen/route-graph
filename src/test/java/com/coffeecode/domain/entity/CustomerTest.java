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
import com.coffeecode.domain.validation.exceptions.ValidationException;

@DisplayName("Customer Entity Tests")
class CustomerTest {

    private Coordinate location;
    private WaterDemand demand;

    @BeforeEach
    void setUp() {
        location = Coordinate.of(0.0, 0.0);
        demand = new WaterDemand(new Volume(100.0));
    }

    @Test
    @DisplayName("Should create customer with valid parameters")
    void shouldCreateCustomerWithValidParameters() {
        Customer customer = new Customer("Customer1", location, demand);

        assertNotNull(customer.getId());
        assertEquals("Customer1", customer.getName());
        assertEquals(location, customer.getLocation());
        assertEquals(demand, customer.getWaterDemand());
        assertEquals(NetworkNode.NodeType.CUSTOMER, customer.getType());
    }

    @Test
    @DisplayName("Should generate unique IDs for different customers")
    void shouldGenerateUniqueIds() {
        Customer customer1 = new Customer("Customer1", location, demand);
        Customer customer2 = new Customer("Customer2", location, demand);

        assertNotEquals(customer1.getId(), customer2.getId());
    }

    @Test
    @DisplayName("Should throw exception for null name")
    void shouldThrowExceptionForNullName() {
        NullPointerException exceptionForNullName = assertThrows(NullPointerException.class,
                () -> new Customer(null, location, demand));
        assertNotNull(exceptionForNullName);
    }

    @Test
    @DisplayName("Should throw exception for null location")
    void shouldThrowExceptionForNullLocation() {
        NullPointerException exceptionForNullLocation = assertThrows(NullPointerException.class,
                () -> new Customer("Customer1", null, demand));
        assertNotNull(exceptionForNullLocation);
    }

    // Removed duplicate method shouldThrowExceptionForNullWaterDemand
    @Test
    @DisplayName("Should throw ValidationException for empty name")
    void shouldThrowExceptionForEmptyName() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> new Customer("  ", location, demand));
        assertEquals("Customer name cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw ValidationException for null water demand")
    void shouldThrowExceptionForNullWaterDemand() {
        ValidationException exception = assertThrows(ValidationException.class,
                () -> new Customer("Customer1", location, null));
        assertEquals("Water demand cannot be null", exception.getMessage());
    }

    @Test
    void shouldImplementToString() {
        Customer customer = new Customer("Customer1", location, demand);
        String toString = customer.toString();

        assertTrue(toString.contains("Customer1"));
        assertTrue(toString.contains("CUSTOMER"));
        assertTrue(toString.contains(location.toString()));
    }
}

package com.coffeecode.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.values.location.Coordinate;
import com.coffeecode.domain.values.water.WaterDemand;
import com.coffeecode.domain.values.water.WaterVolume;
import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("WaterCustomer Tests")
class WaterCustomerTest {

    @Nested
    @DisplayName("Creation Tests")
    class CreationTests {
        @Test
        @DisplayName("Should create customer with valid parameters")
        void shouldCreateCustomerWithValidParameters() {
            WaterCustomer customer = WaterCustomer.builder()
                    .name("Test Customer")
                    .waterDemand(WaterDemand.of(0.5))
                    .location(Coordinate.of(0, 0))
                    .build();

            assertNotNull(customer);
            assertEquals("Test Customer", customer.getName());
            assertEquals(0.5, customer.getWaterDemand().getValue());
            assertEquals(NodeType.CUSTOMER, customer.getType());
        }
    }

    @Nested
    @DisplayName("Validation Tests")
    class ValidationTests {
        @Test
        @DisplayName("Should throw on null name")
        void shouldThrowOnNullName() {
            WaterVolume volume = WaterVolume.of(0.5);
            assertThrows(ValidationException.class, () -> WaterCustomer.builder()
                    .waterDemand(WaterDemand.of(volume.getValue()))
                    .location(Coordinate.of(0, 0))
                    .build());
        }

        @Test
        @DisplayName("Should throw on null demand")
        void shouldThrowOnNullDemand() {
            assertThrows(ValidationException.class, () -> WaterCustomer.builder()
                    .name("Test Customer")
                    .location(Coordinate.of(0, 0))
                    .build());
        }

        @Test
        @DisplayName("Should throw on null location")
        void shouldThrowOnNullLocation() {
            WaterVolume volume = WaterVolume.of(0.5);
            assertThrows(ValidationException.class, () -> WaterCustomer.builder()
                    .name("Test Customer")
                    .waterDemand(WaterDemand.of(volume.getValue()))
                    .build());
        }
    }

    @Nested
    @DisplayName("Builder Tests")
    class BuilderTests {
        @Test
        @DisplayName("Should set node type to CUSTOMER")
        void shouldSetNodeTypeToCustomer() {
            WaterCustomer customer = WaterCustomer.builder()
                    .name("Test Customer")
                    .waterDemand(WaterDemand.of(WaterVolume.of(0.5).getValue()))
                    .location(Coordinate.of(0, 0))
                    .build();

            assertEquals(NodeType.CUSTOMER, customer.getType());
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCaseTests {
        @Test
        @DisplayName("Should handle minimum valid demand")
        void shouldHandleMinimumValidDemand() {
            WaterCustomer customer = WaterCustomer.builder()
                    .name("Min Test")
                    .waterDemand(WaterDemand.of(0.001))
                    .location(Coordinate.of(0, 0))
                    .build();

            assertNotNull(customer);
            assertEquals(0.001, customer.getWaterDemand().getValue());
        }

        @Test
        @DisplayName("Should generate unique IDs")
        void shouldGenerateUniqueIds() {
            Set<UUID> ids = new HashSet<>();
            for (int i = 0; i < 100; i++) {
                WaterCustomer customer = WaterCustomer.builder()
                        .name("Test " + i)
                        .waterDemand(WaterDemand.of(1.0))
                        .location(Coordinate.of(0, 0))
                        .build();
                assertTrue(ids.add(customer.getId()));
            }
        }
    }
}

package com.coffeecode.logic.flow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coffeecode.domain.constants.PhysicalConstants;
import com.coffeecode.domain.constants.SimulationDefaults;
import com.coffeecode.domain.entities.Customer;
import com.coffeecode.domain.entities.NetworkNode;
import com.coffeecode.domain.entities.Pipe;
import com.coffeecode.domain.entities.WaterSource;
import com.coffeecode.domain.values.Coordinate;
import com.coffeecode.domain.values.Distance;
import com.coffeecode.domain.values.PipeProperties;
import com.coffeecode.domain.values.Volume;
import com.coffeecode.domain.values.WaterDemand;
import com.coffeecode.service.flow.FlowCalculationServiceImpl;
import com.coffeecode.service.flow.FlowResult;
import com.coffeecode.service.flow.calculation.HeadLossCalculator;
import com.coffeecode.service.flow.calculation.PressureCalculator;
import com.coffeecode.service.flow.calculation.VelocityCalculator;
import com.coffeecode.validation.exceptions.ValidationException;

@DisplayName("Flow Calculation Service Tests")
class FlowCalculationServiceImplTest {

    private static class TestVelocityCalculator extends VelocityCalculator {

        private double velocityResult = 2.0;
        private double reynoldsResult = 100000.0;

        @Override
        public double calculate(double pressure) {
            return velocityResult;
        }

        @Override
        public double calculateWithReynolds(double velocity, double diameter) {
            return reynoldsResult;
        }
    }

    private static class TestHeadLossCalculator extends HeadLossCalculator {

        private double frictionFactor = 0.02;
        private double headLoss = 5.0;

        @Override
        public double calculateFrictionFactor(double reynolds, double diameter, double roughness) {
            return frictionFactor;
        }

        @Override
        public double calculate(double length, double diameter, double frictionFactor, double velocity) {
            return headLoss;
        }
    }

    private static class TestPressureCalculator extends PressureCalculator {

        private double pressureOutResult = 40000.0;

        @Override
        public double calculatePressureOut(double pressureIn, double headLoss) {
            return pressureOutResult;
        }
    }

    private FlowCalculationServiceImpl service;
    private Pipe pipe;
    private TestVelocityCalculator velocityCalculator;
    private TestHeadLossCalculator headLossCalculator;
    private TestPressureCalculator pressureCalculator;

    @BeforeEach
    void setUp() {
        velocityCalculator = new TestVelocityCalculator();
        headLossCalculator = new TestHeadLossCalculator();
        pressureCalculator = new TestPressureCalculator();

        service = new FlowCalculationServiceImpl(
                velocityCalculator,
                headLossCalculator,
                pressureCalculator
        );

        setupTestPipe();
    }

    @Test
    @DisplayName("Should calculate flow with valid parameters")
    void shouldCalculateFlowWithValidParameters() {
        double pressureIn = 50000.0;
        FlowResult result = service.calculateFlow(pipe, pressureIn);

        assertNotNull(result);
        assertTrue(result.getFlowRate() > 0);
        assertEquals(40000.0, result.getPressureOut());
        assertEquals(2.0, result.getVelocityOut());
        assertEquals(5.0, result.getHeadLoss());
    }

    @Test
    @DisplayName("Should use defaults when pressure is zero")
    void shouldUseDefaultsWhenPressureIsZero() {
        FlowResult result = service.calculateFlow(pipe, 0);

        assertEquals(SimulationDefaults.FLOW_VELOCITY, result.getVelocityOut());
        assertEquals(PhysicalConstants.ATMOSPHERIC_PRESSURE, result.getPressureOut());
        assertEquals(0.0, result.getHeadLoss());
    }

    @Test
    @DisplayName("Should throw exception for null pipe")
    void shouldThrowExceptionForNullPipe() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> service.calculateFlow(null, 50000.0)
        );
        assertTrue(exception.getMessage().contains("Pipe cannot be null"));
    }

    @Test
    @DisplayName("Should throw exception for negative pressure")
    void shouldThrowExceptionForNegativePressure() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> service.calculateFlow(pipe, -1000.0)
        );
        assertTrue(exception.getMessage().contains("Pressure cannot be negative"));
    }

    @Test
    @DisplayName("Should throw exception for zero pipe length")
    void shouldThrowExceptionForZeroPipeLength() {
        PipeProperties invalidProperties = PipeProperties.builder()
                .length(Distance.of(0.0)) // Zero length
                .capacity(Volume.of(500.0))
                .diameter(0.5)
                .roughness(0.0002)
                .build();

        Pipe invalidPipe = createInvalidPipe(invalidProperties);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> service.calculateFlow(invalidPipe, 50000.0)
        );
        assertEquals("Pipe length must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for invalid pipe diameter")
    void shouldThrowExceptionForInvalidPipeDiameter() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> PipeProperties.builder()
                        .length(Distance.of(100.0))
                        .capacity(Volume.of(500.0))
                        .diameter(0.0)
                        .roughness(0.0002)
                        .build()
        );
        assertEquals("Diameter must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception for negative pipe diameter")
    void shouldThrowExceptionForNegativePipeDiameter() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> PipeProperties.builder()
                        .length(Distance.of(100.0))
                        .capacity(Volume.of(500.0))
                        .diameter(-1.0)
                        .roughness(0.0002)
                        .build()
        );
        assertEquals("Diameter must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Should validate pipe properties successfully")
    void shouldValidatePipePropertiesSuccessfully() {
        PipeProperties properties = PipeProperties.builder()
                .length(Distance.of(100.0))
                .capacity(Volume.of(500.0))
                .diameter(0.5)
                .roughness(0.0002)
                .build();

        assertNotNull(properties);
        assertEquals(0.5, properties.getDiameter());
    }

    private Pipe createInvalidPipe(PipeProperties properties) {
        return Pipe.builder()
                .source(pipe.getSource())
                .destination(pipe.getDestination())
                .properties(properties)
                .build();
    }

    private void setupTestPipe() {
        PipeProperties properties = PipeProperties.builder()
                .length(Distance.of(100.0))
                .capacity(Volume.of(500.0))
                .diameter(0.5)
                .roughness(0.0002)
                .build();

        NetworkNode source = WaterSource.builder()
                .name("Source")
                .location(Coordinate.of(0.0, 0.0))
                .capacity(Volume.of(1000.0))
                .build();

        NetworkNode destination = Customer.builder()
                .name("Customer")
                .location(Coordinate.of(1.0, 1.0))
                .waterDemand(WaterDemand.of(Volume.of(100.0)))
                .build();

        pipe = Pipe.builder()
                .source(source)
                .destination(destination)
                .properties(properties)
                .build();
    }
}

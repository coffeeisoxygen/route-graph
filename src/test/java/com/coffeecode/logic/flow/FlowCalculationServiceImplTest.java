package com.coffeecode.logic.flow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.coffeecode.domain.constants.PhysicalConstants;
import com.coffeecode.domain.constants.SimulationDefaults;
import com.coffeecode.domain.entity.Customer;
import com.coffeecode.domain.entity.NetworkNode;
import com.coffeecode.domain.entity.Pipe;
import com.coffeecode.domain.entity.WaterSource;
import com.coffeecode.domain.objects.Coordinate;
import com.coffeecode.domain.objects.Distance;
import com.coffeecode.domain.objects.PipeProperties;
import com.coffeecode.domain.objects.Volume;
import com.coffeecode.domain.objects.WaterDemand;
import com.coffeecode.logic.flow.calculation.HeadLossCalculator;
import com.coffeecode.logic.flow.calculation.PressureCalculator;
import com.coffeecode.logic.flow.calculation.VelocityCalculator;
import com.coffeecode.validation.exceptions.ValidationException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Flow Calculation Service Tests")
class FlowCalculationServiceImplTest {

    @Mock private VelocityCalculator velocityCalculator;
    @Mock private HeadLossCalculator headLossCalculator;
    @Mock private PressureCalculator pressureCalculator;

    private FlowCalculationServiceImpl service;
    private Pipe pipe;

    @BeforeEach
    void setUp() {
        service = new FlowCalculationServiceImpl(
            velocityCalculator,
            headLossCalculator,
            pressureCalculator
        );

        // Setup test data without mocks
        setupTestPipe();
    }

    @Test
    @DisplayName("Should calculate flow with valid parameters")
    void shouldCalculateFlowWithValidParameters() {
        // Setup mocks for this specific test
        when(velocityCalculator.calculate(anyDouble())).thenReturn(2.0);
        when(velocityCalculator.calculateWithReynolds(anyDouble(), anyDouble())).thenReturn(100000.0);
        when(headLossCalculator.calculateFrictionFactor(anyDouble(), anyDouble(), anyDouble())).thenReturn(0.02);
        when(headLossCalculator.calculate(anyDouble(), anyDouble(), anyDouble(), anyDouble())).thenReturn(5.0);
        when(pressureCalculator.calculatePressureOut(anyDouble(), anyDouble())).thenReturn(40000.0);

        double pressureIn = 50000.0;
        FlowResult result = service.calculateFlow(pipe, pressureIn);

        assertNotNull(result);
        assertTrue(result.getFlowRate() > 0);
        assertEquals(40000.0, result.getPressureOut());
    }

    @Test
    @DisplayName("Should use defaults when pressure is zero")
    void shouldUseDefaultsWhenPressureIsZero() {
        FlowResult result = service.calculateFlow(pipe, 0);

        assertEquals(SimulationDefaults.FLOW_VELOCITY, result.getVelocityOut());
        assertEquals(PhysicalConstants.ATMOSPHERIC_PRESSURE, result.getPressureOut());
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

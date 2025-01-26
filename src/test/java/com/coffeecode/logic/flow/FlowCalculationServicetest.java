package com.coffeecode.logic.flow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

@DisplayName("Flow Calculation Service Tests")
class FlowCalculationServiceTest {

    private FlowCalculationService service;
    private Pipe pipe;
    private NetworkNode source;
    private NetworkNode destination;

    @BeforeEach
    void setUp() {
        service = new FlowCalculationServiceImpl();

        PipeProperties properties = PipeProperties.builder()
                .length(Distance.of(100.0))
                .capacity(Volume.of(500.0))
                .diameter(0.5)
                .roughness(0.0002)
                .build();

        source = WaterSource.builder()
                .name("Source")
                .location(Coordinate.of(0.0, 0.0))
                .capacity(Volume.of(1000.0))
                .build();

        destination = Customer.builder()
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

    @Test
    @DisplayName("Should calculate flow with valid parameters")
    void shouldCalculateFlowWithValidParameters() {
        double pressureIn = 50000.0;
        FlowResult result = service.calculateFlow(pipe, pressureIn);

        assertNotNull(result);
        assertTrue(result.getFlowRate() > 0);
        assertTrue(result.getPressureOut() < pressureIn);
    }

    @Test
    @DisplayName("Should use defaults when pressure is zero")
    void shouldUseDefaultsWhenPressureIsZero() {
        FlowResult result = service.calculateFlow(pipe, 0);

        assertNotNull(result);
        assertTrue(result.getFlowRate() > 0);
        assertEquals(SimulationDefaults.FLOW_VELOCITY, result.getVelocityOut());
    }

    @Test
    @DisplayName("Should calculate consistent results")
    void shouldCalculateConsistentResults() {
        double pressureIn = 50000.0;
        FlowResult result1 = service.calculateFlow(pipe, pressureIn);
        FlowResult result2 = service.calculateFlow(pipe, pressureIn);

        assertEquals(result1.getFlowRate(), result2.getFlowRate());
        assertEquals(result1.getPressureOut(), result2.getPressureOut());
        assertEquals(result1.getVelocityOut(), result2.getVelocityOut());
        assertEquals(result1.getHeadLoss(), result2.getHeadLoss());
    }

    @Test
    @DisplayName("Should respect physical constraints")
    void shouldRespectPhysicalConstraints() {
        double pressureIn = 50000.0;
        FlowResult result = service.calculateFlow(pipe, pressureIn);

        assertTrue(result.getPressureOut() >= 0);
        assertTrue(result.getVelocityOut() <= 10.0); // Reasonable max velocity
        assertTrue(result.getHeadLoss() >= 0);
    }
}

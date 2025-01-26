package com.coffeecode.logic.flow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.algorithm.Length;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.coffeecode.domain.entity.Pipe;
import com.coffeecode.logic.flow.calculation.HeadLossCalculator;
import com.coffeecode.logic.flow.calculation.PressureCalculator;
import com.coffeecode.logic.flow.calculation.VelocityCalculator;
import com.coffeecode.validation.exceptions.ValidationException;

class FlowCalculationServiceImplTest {

    @Mock
    private VelocityCalculator velocityCalculator;

    @Mock
    private HeadLossCalculator headLossCalculator;

    @Mock
    private PressureCalculator pressureCalculator;

    @InjectMocks
    private FlowCalculationServiceImpl flowCalculationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateFlow() {
        Pipe pipe = new Pipe(0.5, 0.0001, new Length(100.0));
        double pressureIn = 1000.0;

        when(velocityCalculator.calculate(pressureIn)).thenReturn(2.0);
        when(velocityCalculator.calculateWithReynolds(2.0, 0.5)).thenReturn(10000.0);
        when(headLossCalculator.calculateFrictionFactor(10000.0, 0.5, 0.0001)).thenReturn(0.02);
        when(headLossCalculator.calculate(100.0, 0.5, 0.02, 2.0)).thenReturn(10.0);
        when(pressureCalculator.calculatePressureOut(1000.0, 10.0)).thenReturn(900.0);

        FlowResult result = flowCalculationService.calculateFlow(pipe, pressureIn);

        assertEquals(0.7853981633974483, result.getFlowRate(), 1e-6);
        assertEquals(900.0, result.getPressureOut(), 1e-6);
        assertEquals(2.0, result.getVelocityOut(), 1e-6);
        assertEquals(10.0, result.getHeadLoss(), 1e-6);
    }

    @Test
    void testCalculateFlowWithInvalidPressure() {
        Pipe pipe = new Pipe(0.5, 0.0001, new Length(100.0));
        double pressureIn = -100.0;

        assertThrows(ValidationException.class, () -> flowCalculationService.calculateFlow(pipe, pressureIn));
    }

    @Test
    void testCalculateFlowWithZeroPressure() {
        Pipe pipe = new Pipe(0.5, 0.0001, new Length(100.0));
        double pressureIn = 0.0;

        FlowResult result = flowCalculationService.calculateFlow(pipe, pressureIn);

        assertEquals(0.7853981633974483, result.getFlowRate(), 1e-6);
        assertEquals(101325.0, result.getPressureOut(), 1e-6);
        assertEquals(1.0, result.getVelocityOut(), 1e-6);
        assertEquals(0.0, result.getHeadLoss(), 1e-6);
    }
}

package com.coffeecode.logic.flow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FlowResult Tests")
class FlowResultTest {

    @Test
    @DisplayName("Should create valid FlowResult")
    void shouldCreateValidFlowResult() {
        FlowResult flowResult = FlowResult.builder()
                .flowRate(10.0)
                .pressureOut(5000.0)
                .velocityOut(3.0)
                .headLoss(0.5)
                .build();

        assertNotNull(flowResult);
        assertEquals(10.0, flowResult.getFlowRate());
        assertEquals(5000.0, flowResult.getPressureOut());
        assertEquals(3.0, flowResult.getVelocityOut());
        assertEquals(0.5, flowResult.getHeadLoss());
    }

    @Test
    @DisplayName("Should properly implement equals and hashCode")
    void shouldImplementEqualsAndHashCode() {
        FlowResult flowResult1 = FlowResult.builder()
                .flowRate(10.0)
                .pressureOut(5000.0)
                .velocityOut(3.0)
                .headLoss(0.5)
                .build();

        FlowResult flowResult2 = FlowResult.builder()
                .flowRate(10.0)
                .pressureOut(5000.0)
                .velocityOut(3.0)
                .headLoss(0.5)
                .build();

        FlowResult flowResult3 = FlowResult.builder()
                .flowRate(15.0)
                .pressureOut(6000.0)
                .velocityOut(4.0)
                .headLoss(0.7)
                .build();

        assertEquals(flowResult1, flowResult2);
        assertNotSame(flowResult1, flowResult3);
        assertEquals(flowResult1.hashCode(), flowResult2.hashCode());
    }

    @Test
    @DisplayName("Should implement toString properly")
    void shouldImplementToString() {
        FlowResult flowResult = FlowResult.builder()
                .flowRate(10.0)
                .pressureOut(5000.0)
                .velocityOut(3.0)
                .headLoss(0.5)
                .build();

        String expected = "FlowResult(flowRate=10.0, pressureOut=5000.0, velocityOut=3.0, headLoss=0.5)";
        assertEquals(expected, flowResult.toString());
    }
}

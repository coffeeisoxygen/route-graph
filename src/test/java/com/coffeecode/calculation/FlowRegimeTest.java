package com.coffeecode.calculation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FlowRegimeTest {

    @Test
    void testLaminarFlowRegime() {
        assertEquals(FlowRegime.LAMINAR, FlowRegime.fromReynolds(1000));
    }

    @Test
    void testTransitionFlowRegime() {
        assertEquals(FlowRegime.TRANSITION, FlowRegime.fromReynolds(3000));
    }

    @Test
    void testTurbulentFlowRegime() {
        assertEquals(FlowRegime.TURBULENT, FlowRegime.fromReynolds(5000));
    }

    @Test
    void testBoundaryLaminarToTransition() {
        assertEquals(FlowRegime.TRANSITION, FlowRegime.fromReynolds(2300));
    }

    @Test
    void testBoundaryTransitionToTurbulent() {
        assertEquals(FlowRegime.TURBULENT, FlowRegime.fromReynolds(4000));
    }
}

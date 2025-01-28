package com.coffeecode.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coffeecode.core.NetworkTopology;
import com.coffeecode.core.RouterNode;
import com.coffeecode.core.ServerNode;

class BaseNetworkValidatorTest {
    private NetworkValidator validator;
    private NetworkTopology topology;

    @BeforeEach
    void setUp() {
        validator = new BaseNetworkValidator();
        topology = new NetworkTopology();
    }

    @Test
    void testEmptyTopology() {
        List<ValidationError> errors = validator.validate(topology);
        assertFalse(validator.isValid(topology));
        assertEquals(1, errors.size());
        assertEquals("NO_NODES", errors.get(0).getCode());
    }

    @Test
    void testValidTopology() {
        RouterNode router = new RouterNode("R1", 10);
        ServerNode server = new ServerNode("S1", 100, 1000);

        topology.addNode(router);
        topology.addNode(server);
        topology.connect("R1", "S1", 100, 5);

        List<ValidationError> errors = validator.validate(topology);
        assertTrue(validator.isValid(topology),
                "Topology should be valid with error list: " + errors);
    }
}

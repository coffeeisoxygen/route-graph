package com.coffeecode.domain.entities.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.coffeecode.domain.constants.OperationalLimits;
import com.coffeecode.domain.entities.NetworkNode;
import com.coffeecode.domain.entities.NodeType;
import com.coffeecode.domain.values.location.Coordinate;
import com.coffeecode.domain.values.location.Elevation;

public abstract class BaseNodeTest {
    // Test Fixtures
    protected static final Coordinate DEFAULT_LOCATION = Coordinate.of(0, 0);
    protected static final Elevation DEFAULT_ELEVATION = Elevation.of(100.0);
    protected static final String DEFAULT_NAME = "Test Node";
    protected static final NodeType DEFAULT_TYPE = NodeType.JUNCTION;

    // Abstract method to get builder
    protected abstract NetworkNode.AbstractNodeBuilder<?> createBuilder();

    // Common Test Utils
    protected NetworkNode createDefaultNode() {
        return createBuilder()
                .location(DEFAULT_LOCATION)
                .elevation(DEFAULT_ELEVATION)
                .type(DEFAULT_TYPE)
                .build();
    }

    // Shared Assertions
    protected void assertValidNode(NetworkNode node) {
        assertNotNull(node);
        assertNotNull(node.getId());
        assertValidLocation(node.getLocation());
        assertValidElevation(node.getElevation());
        assertValidType(node.getType());
    }

    protected void assertValidLocation(Coordinate location) {
        assertNotNull(location);
        assertTrue(location.getLatitude() >= -90 && location.getLatitude() <= 90);
        assertTrue(location.getLongitude() >= -180 && location.getLongitude() <= 180);
    }

    protected void assertValidElevation(Elevation elevation) {
        assertNotNull(elevation);
        assertTrue(elevation.getValue() >= OperationalLimits.ElevationLimits.MIN);
        assertTrue(elevation.getValue() <= OperationalLimits.ElevationLimits.MAX);
    }

    protected void assertValidType(NodeType type) {
        assertNotNull(type);
    }

    protected void assertUniqueIds(int count) {
        Set<UUID> ids = new HashSet<>();
        for (int i = 0; i < count; i++) {
            NetworkNode node = createDefaultNode();
            assertTrue(ids.add(node.getId()), "IDs must be unique");
        }
        assertEquals(count, ids.size());
    }

    protected void assertImmutableLocation(NetworkNode node, Coordinate original) {
        assertNotSame(original, node.getLocation());
        assertEquals(original.getLatitude(), node.getLocation().getLatitude());
        assertEquals(original.getLongitude(), node.getLocation().getLongitude());
    }

    protected void assertImmutableElevation(NetworkNode node, Elevation original) {
        assertNotSame(original, node.getElevation());
        assertEquals(original.getValue(), node.getElevation().getValue());
    }
}

package com.coffeecode.network.nodes;

import com.coffeecode.location.coordinates.api.Coordinates;
import com.coffeecode.location.elevations.model.Elevation;
import com.coffeecode.network.distribution.capacity.Capacity;
import com.coffeecode.network.distribution.flow.FlowRate;
import com.coffeecode.network.distribution.pressure.HeadPressure;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WaterSource extends WaterNodes {
    private final Capacity capacity;
    private final FlowRate flowRate;
    private final HeadPressure headPressure;

    // Using static builder instead of Lombok @Builder
    public static WaterSourceBuilder builder() {
        return new WaterSourceBuilder();
    }

    private WaterSource(String name, Coordinates coordinates, Elevation elevation,
            Capacity capacity, FlowRate flowRate, HeadPressure headPressure) {
        super(name, coordinates, elevation, NodeType.SOURCE);
        this.capacity = capacity;
        this.flowRate = flowRate;
        this.headPressure = headPressure;
    }

    public static class WaterSourceBuilder {
        private String name;
        private Coordinates coordinates;
        private Elevation elevation;
        private Capacity capacity;
        private FlowRate flowRate;
        private HeadPressure headPressure;

        public WaterSourceBuilder name(String name) {
            this.name = name;
            return this;
        }

        public WaterSourceBuilder coordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        public WaterSourceBuilder elevation(Elevation elevation) {
            this.elevation = elevation;
            return this;
        }

        public WaterSourceBuilder capacity(Capacity capacity) {
            this.capacity = capacity;
            return this;
        }

        public WaterSourceBuilder flowRate(FlowRate flowRate) {
            this.flowRate = flowRate;
            return this;
        }

        public WaterSourceBuilder headPressure(HeadPressure headPressure) {
            this.headPressure = headPressure;
            return this;
        }

        public WaterSource build() {
            validateFields();
            return new WaterSource(name, coordinates, elevation, capacity, flowRate, headPressure);
        }

        private void validateFields() {
            if (name == null)
                throw new IllegalArgumentException("Name cannot be null");
            if (coordinates == null)
                throw new IllegalArgumentException("Coordinates cannot be null");
            if (elevation == null)
                throw new IllegalArgumentException("Elevation cannot be null");
            if (capacity == null)
                throw new IllegalArgumentException("Capacity cannot be null");
            if (flowRate == null)
                throw new IllegalArgumentException("Flow rate cannot be null");
            if (headPressure == null)
                throw new IllegalArgumentException("Head pressure cannot be null");
        }
    }
}

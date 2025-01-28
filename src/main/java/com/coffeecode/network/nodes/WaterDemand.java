package com.coffeecode.network.nodes;

import com.coffeecode.location.coordinates.api.Coordinates;
import com.coffeecode.location.elevations.model.Elevation;
import com.coffeecode.network.distribution.flow.FlowRate;
import com.coffeecode.network.distribution.pressure.HeadPressure;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WaterDemand extends WaterNodes {
    private final FlowRate demand;
    private final HeadPressure minPressure;

    public static WaterDemandBuilder builder() {
        return new WaterDemandBuilder();
    }

    private WaterDemand(String name, Coordinates coordinates, Elevation elevation,
            FlowRate demand, HeadPressure minPressure) {
        super(name, coordinates, elevation, NodeType.DEMAND);
        this.demand = demand;
        this.minPressure = minPressure;
    }

    public static class WaterDemandBuilder {
        private String name;
        private Coordinates coordinates;
        private Elevation elevation;
        private FlowRate demand;
        private HeadPressure minPressure;

        public WaterDemandBuilder name(String name) {
            this.name = name;
            return this;
        }

        public WaterDemandBuilder coordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        public WaterDemandBuilder elevation(Elevation elevation) {
            this.elevation = elevation;
            return this;
        }

        public WaterDemandBuilder demand(FlowRate demand) {
            this.demand = demand;
            return this;
        }

        public WaterDemandBuilder minPressure(HeadPressure minPressure) {
            this.minPressure = minPressure;
            return this;
        }

        public WaterDemand build() {
            validateFields();
            return new WaterDemand(name, coordinates, elevation, demand, minPressure);
        }

        private void validateFields() {
            if (name == null)
                throw new IllegalArgumentException("Name cannot be null");
            if (coordinates == null)
                throw new IllegalArgumentException("Coordinates cannot be null");
            if (elevation == null)
                throw new IllegalArgumentException("Elevation cannot be null");
            if (demand == null)
                throw new IllegalArgumentException("Demand cannot be null");
            if (minPressure == null)
                throw new IllegalArgumentException("Minimum pressure cannot be null");
        }
    }
}

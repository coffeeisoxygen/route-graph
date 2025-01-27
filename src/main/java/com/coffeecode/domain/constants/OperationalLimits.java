package com.coffeecode.domain.constants;

/**
 * Operational limits and parameters for water distribution systems.
 */
public final class OperationalLimits {

    public static final class Flow {

        public static final double MIN_RATE = 0.001;  // m³/s (1 L/s)
        public static final double MAX_RATE = 2.0;    // m³/s (2000 L/s)

        public static final class Default {

            public static final double SMALL_PIPE = 0.05;   // D ≤ 200mm
            public static final double MEDIUM_PIPE = 0.2;   // 200mm < D ≤ 500mm
            public static final double LARGE_PIPE = 0.5;    // D > 500mm

            private Default() {
            }
        }

        private Flow() {
        }
    }

    public static final class Pressure {

        public static final double MIN_SERVICE = 137895.0;  // 20 PSI
        public static final double MAX_SERVICE = 551580.0;  // 80 PSI
        public static final double MIN_HYDRANT = 137895.0;  // Fire hydrant minimum

        private Pressure() {
        }
    }

    public static final class Temperature {

        public static final double MIN_OPERATING = 4.0;   // °C
        public static final double MAX_OPERATING = 30.0;  // °C
        public static final double STANDARD = 20.0;       // °C

        private Temperature() {
        }
    }

    public static final class Elevation {

        public static final double MIN = -100.0;  // m
        public static final double MAX = 5000.0;  // m
        public static final double DEFAULT = 0.0;  // m

        private Elevation() {
        }
    }

    public static final class Simulation {

        public static final double DEFAULT_PIPE_DIAMETER = 0.5;    // m
        public static final double DEFAULT_PIPE_ROUGHNESS = 0.0002; // m
        public static final double DEFAULT_FLOW_VELOCITY = 1.5;    // m/s
        public static final double DEFAULT_PRESSURE = 50000.0;     // Pa

        private Simulation() {
        }
    }

    public static final class Source {

        public static final int MIN_NAME_LENGTH = 3;
        public static final int MAX_NAME_LENGTH = 50;
        public static final double MIN_CAPACITY = 100.0;   // m³
        public static final double MAX_CAPACITY = 1000000.0; // m³

        public static final class PeakFactors {

            public static final double RESIDENTIAL = 2.5;
            public static final double COMMERCIAL = 1.8;
            public static final double INDUSTRIAL = 1.5;

            private PeakFactors() {
            }
        }

        private Source() {
        }
    }

    private OperationalLimits() {
    }
}

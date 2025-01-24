package com.coffeecode.core.benchmark;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;
import com.coffeecode.core.model.Location;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class LocationBenchmark {

    @Benchmark
    public Location benchmarkLocationCreation() {
        return Location.createNew("Test", 106.8456, -6.2088);
    }

    @Benchmark
    public boolean benchmarkCoordinateValidation() {
        try {
            Location.createNew("Test", 200.0, 100.0);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }
}

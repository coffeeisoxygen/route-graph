package com.coffeecode.domain.common;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NameGenerator {

    private static final Map<String, AtomicInteger> sequences = new ConcurrentHashMap<>();

    public static String generateName(String type) {
        AtomicInteger sequence = sequences.computeIfAbsent(type, k -> new AtomicInteger(0));
        return String.format("%s-%d", type.toLowerCase(), sequence.incrementAndGet());
    }

    private NameGenerator() {
        // preventing instantiation
    }
}

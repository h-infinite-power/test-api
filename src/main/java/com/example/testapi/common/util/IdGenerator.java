package com.example.testapi.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final AtomicInteger sequence = new AtomicInteger(0);

    public static String generateId(String prefix) {
        LocalDateTime now = LocalDateTime.now();
        int currentSequence = sequence.getAndUpdate(n -> (n + 1) % 1000);
        return String.format("%s_%s%03d", prefix, now.format(formatter), currentSequence);
    }
}

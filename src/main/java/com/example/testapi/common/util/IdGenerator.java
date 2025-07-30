package com.example.testapi.common.util;

import java.security.SecureRandom;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final AtomicInteger sequence = new AtomicInteger(0);

    private static final long UTC_EPOCH_2020 = 1577836800000L;
    private static final int SHIFT_BIT = 23;
    private static final int BIT_23_VALUE = 0x800000;

    public static Long generateId() {
        long sub = System.currentTimeMillis() - UTC_EPOCH_2020;
        return (sub << SHIFT_BIT) + new SecureRandom().nextInt(BIT_23_VALUE);
    }
}

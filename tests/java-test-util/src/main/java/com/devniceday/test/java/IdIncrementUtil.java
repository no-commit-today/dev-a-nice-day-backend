package com.devniceday.test.java;

import java.util.concurrent.atomic.AtomicLong;

public abstract class IdIncrementUtil {

    private static AtomicLong id = new AtomicLong(0);

    public static long nextId() {
        return id.incrementAndGet();
    }

    private IdIncrementUtil() {
        throw new UnsupportedOperationException();
    }
}

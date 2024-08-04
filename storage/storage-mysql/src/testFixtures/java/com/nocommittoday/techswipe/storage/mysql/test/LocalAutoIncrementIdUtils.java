package com.nocommittoday.techswipe.storage.mysql.test;

public final class LocalAutoIncrementIdUtils {

    private static volatile long id = 1;

    public static synchronized long nextId() {
        return id++;
    }

    private LocalAutoIncrementIdUtils() {
        throw new UnsupportedOperationException();
    }
}
package com.nocommittoday.techswipe.test.storage.mysql;

public class LocalAutoIncrementIdGenerator {

    private long id = 0;

    public synchronized long nextId() {
        return ++id;
    }
}

package com.nocommittoday.techswipe.storage.mysql.test;

public class LocalAutoIncrementIdGenerator {

    private long id = 0;

    public synchronized long nextId() {
        return ++id;
    }
}

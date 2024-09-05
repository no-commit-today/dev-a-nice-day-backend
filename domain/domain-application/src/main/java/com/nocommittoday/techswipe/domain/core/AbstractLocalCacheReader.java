package com.nocommittoday.techswipe.domain.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractLocalCacheReader<K, V> {

    private static final Logger log = LoggerFactory.getLogger(AbstractLocalCacheReader.class);

    private final Map<K, CacheEntry<V>> cache = new ConcurrentHashMap<>();

    private final Clock clock;

    private final Duration ttl;

    private final double beta;

    protected AbstractLocalCacheReader(Duration ttl) {
        this(Clock.systemDefaultZone(), ttl, 1.0);
    }

    protected AbstractLocalCacheReader(Duration ttl, double beta) {
        this(Clock.systemDefaultZone(), ttl, beta);
    }

    protected AbstractLocalCacheReader(Clock clock, Duration ttl, double beta) {
        this.clock = clock;
        this.ttl = ttl;
        this.beta = beta;
    }

    public V get(K key) {
        if (!validate(key)) {
            log.debug("캐시 미스 키: {}", key);
            set(key);
        } else {
            log.debug("캐시 히트 키: {}", key);
        }
        return this.cache.get(key).getValue();
    }

    private void set(K key) {
        long currentTime = this.clock.millis();
        V value = loadData(key);
        long timeToCompute = this.clock.millis() - currentTime;

        long expiry = this.clock.millis() + this.ttl.toMillis();
        this.cache.put(key, new CacheEntry<>(value, expiry, timeToCompute));
    }

    private boolean validate(K key) {
        if (!this.cache.containsKey(key)) {
            return false;
        }
        CacheEntry<V> entry = this.cache.get(key);
        long currentTime = this.clock.millis();
        if (currentTime > entry.getExpiry()) {
            cache.remove(key);
            return false;
        }

        long gapScore = (long) (currentTime
                - (entry.getTimeToCompute() * this.beta * Math.log(ThreadLocalRandom.current().nextDouble())));
        if (gapScore > entry.getExpiry()) {
            return false;
        }

        return true;
    }

    protected abstract V loadData(K key);

    static class CacheEntry<V> {

        private final V value;
        private final long expiry;
        private final long timeToCompute;

        public CacheEntry(V value, long expiry, long timeToCompute) {
            this.value = value;
            this.expiry = expiry;
            this.timeToCompute = timeToCompute;
        }

        public V getValue() {
            return value;
        }

        public long getExpiry() {
            return expiry;
        }

        public long getTimeToCompute() {
            return timeToCompute;
        }
    }
}

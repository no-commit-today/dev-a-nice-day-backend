package com.nocommittoday.techswipe.domain.core;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Tag("develop")
class AbstractLocalCacheReaderDevTest {

    private Duration readDelay = Duration.ofMillis(400);
    private Duration ttl = Duration.ofSeconds(20);
    private double beta = 1.0;
    private int threadNum = 10;
    private Duration experimentDuration = Duration.ofMinutes(5);

    @Test
    void 실험() throws InterruptedException {
        TestReader testReader = new TestReader(readDelay);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(threadNum);
        for (int i = 0; i < 10; i++) {
            executorService.scheduleAtFixedRate(
                    () -> testReader.get("key"),
                    0,
                    ThreadLocalRandom.current().nextLong(5000, 15000),
                    TimeUnit.MILLISECONDS
            );
        }

        Thread.sleep(experimentDuration.toMillis());
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("총 호출 횟수: " + testReader.getCallCount());
    }

    /**
     * PER 적용
     * 26
     * 29
     * 28
     * 39
     * 31
     * 30
     *
     * PER 미적용
     * 30
     * 46
     * 32
     * 31
     * 35
     * 31
     * 30
     */
    @Test
    void 캐시_실험() throws InterruptedException {
        TestReader testReaderInner = new TestReader(readDelay);
        TestReaderLocalCache testReaderLocalCache = new TestReaderLocalCache(
                testReaderInner, ttl, beta);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(threadNum);
        for (int i = 0; i < 10; i++) {
            executorService.scheduleAtFixedRate(
                    () -> testReaderLocalCache.get("key"),
                    0,
                    ThreadLocalRandom.current().nextLong(5000, 15000),
                    TimeUnit.MILLISECONDS
            );
        }

        Thread.sleep(experimentDuration.toMillis());
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("총 호출 횟수: " + testReaderInner.getCallCount());
    }

    static class TestReader {

        private final AtomicInteger callCount = new AtomicInteger(0);

        private final Duration delay;

        public TestReader(Duration delay) {
            this.delay = delay;
        }

        public TestData get(String key) {
            callCount.incrementAndGet();
            try {
                Thread.sleep(delay.toMillis());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return new TestData(key);
        }

        public int getCallCount() {
            return callCount.get();
        }
    }

    static class TestReaderLocalCache extends AbstractLocalCacheReader<String, TestData> {

        private final TestReader testReader;

        public TestReaderLocalCache(TestReader testReader, Duration ttl, double beta) {
            super(ttl, beta);
            this.testReader = testReader;
        }

        @Override
        protected TestData loadData(String key) {
            return testReader.get(key);
        }
    }

    record TestData(String value) {
    }
}
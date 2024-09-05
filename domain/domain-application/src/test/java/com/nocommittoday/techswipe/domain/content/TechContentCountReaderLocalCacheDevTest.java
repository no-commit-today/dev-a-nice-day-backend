package com.nocommittoday.techswipe.domain.content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Tag("develop")
@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver",
        "spring.datasource.url={DATABASE_URL}",
        "spring.datasource.username={DATABASE_USERNAME}",
        "spring.datasource.password={DATABASE_PASSWORD}",
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect"
})
class TechContentCountReaderLocalCacheDevTest {

    @Autowired
    private TechContentCountReader contentCountReader;
    private TechContentCountReaderExperiment techContentCountReaderExperiment;
    private TechContentCountReaderLocalCache techContentCountReaderLocalCache;
    private TechContentCountReaderLocalCacheExperiment techContentCountReaderLocalCacheExperiment;

    private int threadNum = 10;
    private Duration experimentDuration = Duration.ofSeconds(310);

    @BeforeEach
    void setUp() {
        techContentCountReaderExperiment = new TechContentCountReaderExperiment(contentCountReader);
        techContentCountReaderLocalCache = new TechContentCountReaderLocalCache(techContentCountReaderExperiment);
        techContentCountReaderLocalCacheExperiment = new TechContentCountReaderLocalCacheExperiment(techContentCountReaderLocalCache);
    }

    /**
     * PER 적용
     * beta 1.0
     * 30
     * 30
     * beta 2.0
     * 28 / 31010
     * 24 / 31010
     * 28 / 31010
     * beta 3.0
     * 33 / 31010
     * 31 / 31010
     * 27 / 31010
     *
     * PER 미적용
     * 100 / 31010
     * 100 / 31010
     */
    @Test
    void 실험() throws InterruptedException {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(threadNum);
        for (int i = 0; i < 10; i++) {
            executorService.scheduleAtFixedRate(
                    () -> techContentCountReaderLocalCacheExperiment.get(TechCategory.valueList()),
                    0,
                    100,
                    TimeUnit.MILLISECONDS
            );
        }

        Thread.sleep(experimentDuration.toMillis());
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("총 호출 횟수: " + techContentCountReaderLocalCacheExperiment.getCallCount());
        System.out.println("캐시 미스 횟수: " + techContentCountReaderExperiment.getCallCount());
    }

    static class TechContentCountReaderExperiment extends TechContentCountReader {

        private static final Logger log = LoggerFactory.getLogger(TechContentCountReaderExperiment.class);

        private final TechContentCountReader techContentCountReader;
        private final AtomicInteger callCount = new AtomicInteger(0);

        public TechContentCountReaderExperiment(TechContentCountReader techContentCountReader) {
            super(null);
            this.techContentCountReader = techContentCountReader;
        }

        @Override
        public long count(List<TechCategory> categories) {
            log.info("[{}] 캐시 미스", Thread.currentThread().getName());
            callCount.incrementAndGet();
            return techContentCountReader.count(categories);
        }

        public int getCallCount() {
            return callCount.get();
        }
    }

    static class TechContentCountReaderLocalCacheExperiment extends TechContentCountReaderLocalCache {

        private final TechContentCountReaderLocalCache techContentCountReaderLocalCache;
        private final AtomicInteger callCount = new AtomicInteger(0);

        public TechContentCountReaderLocalCacheExperiment(TechContentCountReaderLocalCache techContentCountReaderLocalCache) {
            super(null);
            this.techContentCountReaderLocalCache = techContentCountReaderLocalCache;
        }

        @Override
        public Long get(List<TechCategory> categories) {
            callCount.incrementAndGet();
            return techContentCountReaderLocalCache.get(categories);
        }

        public int getCallCount() {
            return callCount.get();
        }
    }

    @Test
    void practice() {
        System.out.println(getGapScore());
        System.out.println(getGapScore());
        System.out.println(getGapScore());
        System.out.println(getGapScore());
        System.out.println(getGapScore());
        System.out.println(getGapScore());
        System.out.println(getGapScore());
        System.out.println(getGapScore());
        System.out.println(getGapScore());
        System.out.println(getGapScore());
        System.out.println(getGapScore());
        System.out.println(getGapScore());
    }

    private double getGapScore() {
        return Math.log(ThreadLocalRandom.current().nextDouble());
    }

}
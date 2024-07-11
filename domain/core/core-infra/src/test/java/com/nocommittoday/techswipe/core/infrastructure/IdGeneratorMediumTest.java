package com.nocommittoday.techswipe.core.infrastructure;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

class IdGeneratorMediumTest {

    private IdGenerator idGenerator = new IdGenerator(new SystemClockHolder());

    @Test
    void 유니크한_아이디를_생성한다() {
        // given
        final int iterations = 10_000;

        // when
        final Set<Long> idSet = new HashSet<>();
        for (int i = 0; i < iterations; i++) {
            idSet.add(idGenerator.nextId());
        }

        // then
        assertThat(idSet).hasSize(iterations);
    }

    @Test
    void 멀티스레드_환경에서도_유니크한_아이디를_생성한다() throws InterruptedException, ExecutionException {
        // given
        final int numThreads = 50;
        final ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        final CountDownLatch latch = new CountDownLatch(numThreads);

        final int iterations = 10_000;

        // when
        final List<Future<Long>> futures = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            final Future<Long> future = executorService.submit(() -> {
                try {
                    return idGenerator.nextId();
                } finally {
                    latch.countDown();
                }
            });
            futures.add(future);
        }

        latch.await();

        final List<Long> idList = new ArrayList<>();
        for (Future<Long> future : futures) {
            idList.add(future.get());
        }

        // then
        assertThat(idList.stream().distinct().count()).isEqualTo(iterations);
    }
}

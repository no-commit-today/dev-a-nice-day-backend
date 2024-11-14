package com.devniceday.module.idgenerator;

import com.devniceday.module.idgenerator.IdGenerator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.Clock;
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

@Tag("context")
class IdGeneratorMediumTest {

    private IdGenerator idGenerator = new IdGenerator(Clock.systemDefaultZone());

    @Test
    void 유니크한_아이디를_생성한다() {
        // given
        int iterations = 10_000;

        // when
        Set<Long> idSet = new HashSet<>();
        for (int i = 0; i < iterations; i++) {
            idSet.add(idGenerator.nextId());
        }

        // then
        assertThat(idSet).hasSize(iterations);
    }

    @Test
    void 멀티스레드_환경에서도_유니크한_아이디를_생성한다() throws InterruptedException, ExecutionException {
        // given
        int numThreads = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        int iterations = 10_000;

        // when
        List<Future<Long>> futures = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            Future<Long> future = executorService.submit(() -> {
                try {
                    return idGenerator.nextId();
                } finally {
                    latch.countDown();
                }
            });
            futures.add(future);
        }

        latch.await();

        List<Long> idList = new ArrayList<>();
        for (Future<Long> future : futures) {
            idList.add(future.get());
        }

        // then
        assertThat(idList.stream().distinct().count()).isEqualTo(iterations);
    }
}

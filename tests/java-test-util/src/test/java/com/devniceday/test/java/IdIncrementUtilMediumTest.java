package com.devniceday.test.java;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("context")
class IdIncrementUtilMediumTest {

    @Test
    void 아이디_증가는_동시성을_지원한다() throws ExecutionException, InterruptedException {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // when
        List<Future<Long>> futures = new ArrayList<>();
        for (int i = 0; i < 10_000; i++) {
            futures.add(executorService.submit(IdIncrementUtil::nextId));
        }

        // then
        List<Long> result = new ArrayList<>();
        for (Future<Long> future : futures) {
            result.add(future.get());
        }

        assertThat(result).containsOnly(
                LongStream.rangeClosed(1, 10_000).boxed().toArray(Long[]::new)
        );
    }
}
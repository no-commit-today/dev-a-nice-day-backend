package com.nocommittoday.module.test.racecondition;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class RaceConditionExecutor<T> {

    private final Consumer<T> consumer;

    private final List<T> items;

    public static RaceConditionExecutor<Integer> count(final int count, final Consumer<Integer> consumer) {
        return new RaceConditionExecutor<>(
                IntStream.range(0, count).boxed().toList(),
                consumer
        );
    }

    public static <T> RaceConditionExecutor<T> list(final List<T> items, final Consumer<T> consumer) {
        return new RaceConditionExecutor<>(items, consumer);
    }

    public RaceConditionExecutor(final List<T> items, final Consumer<T> consumer) {
        this.consumer = consumer;
        this.items = items;
    }

    public void execute() throws InterruptedException {
        final int threadCount = items.size();
        final ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        final CountDownLatch latch = new CountDownLatch(threadCount);

        for (T item : items) {
            executorService.submit(() -> {
                try {
                    consumer.accept(item);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
    }

}

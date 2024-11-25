package com.devniceday.batch.job;

import com.devniceday.batch.domain.CollectedContentExceptionProcessor;
import com.devniceday.batch.domain.SubscriptionExceptionProcessor;
import com.devniceday.storage.db.core.BatchCollectedContentAndSubscriptionProjection;
import com.devniceday.storage.db.core.BatchCollectedContentEntity;
import org.springframework.batch.core.SkipListener;

public class CollectedContentInitializeJobSkipListener
        implements SkipListener<BatchCollectedContentAndSubscriptionProjection, BatchCollectedContentEntity> {

    private final SubscriptionExceptionProcessor subscriptionExceptionProcessor;
    private final CollectedContentExceptionProcessor contentExceptionProcessor;

    public CollectedContentInitializeJobSkipListener(
            SubscriptionExceptionProcessor subscriptionExceptionProcessor,
            CollectedContentExceptionProcessor contentExceptionProcessor) {
        this.subscriptionExceptionProcessor = subscriptionExceptionProcessor;
        this.contentExceptionProcessor = contentExceptionProcessor;
    }

    @Override
    public void onSkipInProcess(BatchCollectedContentAndSubscriptionProjection item, Throwable t) {
        subscriptionExceptionProcessor.process(item.getSubscription().getId(), (Exception) t);
        contentExceptionProcessor.process(item.getCollectedContent().getId(), (Exception) t);

    }
}

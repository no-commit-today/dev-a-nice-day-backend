package com.devniceday.batch.job;

import com.devniceday.batch.domain.CollectedContentExceptionProcessor;
import com.devniceday.batch.domain.SubscriptionExceptionProcessor;
import com.devniceday.storage.db.core.BatchCollectedContentAndSubscriptionProjection;
import com.devniceday.storage.db.core.BatchCollectedContentEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

public class CollectedContentInitializeJobSkipListener
        implements SkipListener<BatchCollectedContentAndSubscriptionProjection, BatchCollectedContentEntity> {

    private static final Logger log = LoggerFactory.getLogger(CollectedContentInitializeJobSkipListener.class);

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
        log.error("컨텐츠 수집 중 오류 발생하여 disable 처리 합니다. subscriptionId={}",
                item.getSubscription().getId(), t);
        log.error("컨텐츠 수집 중 오류 발생하여 filter 처리 합니다. collectedContentId={}",
                item.getCollectedContent().getId(), t);
        subscriptionExceptionProcessor.process(item.getSubscription().getId(), (Exception) t);
        contentExceptionProcessor.process(item.getCollectedContent().getId(), (Exception) t);

    }
}

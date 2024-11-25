package com.devniceday.batch.job;

import com.devniceday.batch.domain.SubscriptionExceptionProcessor;
import com.devniceday.storage.db.core.BatchCollectedContentEntity;
import com.devniceday.storage.db.core.BatchSubscriptionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

import java.util.List;

public class ContentCollectJobSkipListener
        implements SkipListener<BatchSubscriptionEntity, List<BatchCollectedContentEntity>> {

    private static final Logger log = LoggerFactory.getLogger(ContentCollectJobSkipListener.class);

    private final SubscriptionExceptionProcessor processor;

    public ContentCollectJobSkipListener(SubscriptionExceptionProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void onSkipInProcess(BatchSubscriptionEntity item, Throwable t) {
        log.error("컨텐츠 수집 중 오류 발생하여 disable 처리 합니다. subscriptionId={}", item.getId(), t);
        processor.process(item.getId(), (Exception) t);
    }
}

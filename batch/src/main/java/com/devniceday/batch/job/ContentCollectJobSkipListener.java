package com.devniceday.batch.job;

import com.devniceday.batch.domain.SubscriptionExceptionProcessor;
import com.devniceday.storage.db.core.BatchCollectedContentEntity;
import com.devniceday.storage.db.core.BatchSubscriptionEntity;
import org.springframework.batch.core.SkipListener;

import java.util.List;

public class ContentCollectJobSkipListener
        implements SkipListener<BatchSubscriptionEntity, List<BatchCollectedContentEntity>> {

    private final SubscriptionMapper mapper;
    private final SubscriptionExceptionProcessor processor;

    public ContentCollectJobSkipListener(SubscriptionMapper mapper, SubscriptionExceptionProcessor processor) {
        this.mapper = mapper;
        this.processor = processor;
    }

    @Override
    public void onSkipInProcess(BatchSubscriptionEntity item, Throwable t) {
        processor.process(mapper.map(item), (Exception) t);
    }
}

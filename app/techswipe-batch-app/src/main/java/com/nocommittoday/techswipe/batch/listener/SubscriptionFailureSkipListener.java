package com.nocommittoday.techswipe.batch.listener;

import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

import java.util.List;

public class SubscriptionFailureSkipListener
        implements SkipListener<SubscriptionEntity, List<CollectedContentEntity>> {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionFailureSkipListener.class);

    @Override
    public void onSkipInProcess(SubscriptionEntity item, Throwable t) {
        log.error("subscription.id={}", item.getId(), t);
    }
}

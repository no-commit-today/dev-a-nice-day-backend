package com.nocommittoday.techswipe.batch.listener;

import com.nocommittoday.techswipe.collection.storage.mysql.CollectedContentEntity;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;

import java.util.List;

@Slf4j
public class SubscriptionFailureSkipListener
        implements SkipListener<SubscriptionEntity, List<CollectedContentEntity>> {

    @Override
    public void onSkipInProcess(final SubscriptionEntity item, final Throwable t) {
        log.error("subscription.id={}", item.getId(), t);
    }
}

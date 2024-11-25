package com.devniceday.storage.db.core;

import com.querydsl.core.annotations.QueryProjection;

public class BatchCollectedContentAndSubscriptionProjection {

    private final BatchCollectedContentEntity collectedContent;
    private final BatchSubscriptionEntity subscription;

    @QueryProjection
    public BatchCollectedContentAndSubscriptionProjection(
            BatchCollectedContentEntity collectedContent, BatchSubscriptionEntity subscription) {
        this.collectedContent = collectedContent;
        this.subscription = subscription;
    }

    public BatchCollectedContentEntity getCollectedContent() {
        return collectedContent;
    }

    public BatchSubscriptionEntity getSubscription() {
        return subscription;
    }
}

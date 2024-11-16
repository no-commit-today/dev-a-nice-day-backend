package com.devniceday.batch.domain;


import jakarta.annotation.Nullable;

public record Subscription(
        long id,
        long providerId,
        SubscriptionType type,
        @Nullable FeedSubscription feed,
        @Nullable WebListSubscription webList
) {
}

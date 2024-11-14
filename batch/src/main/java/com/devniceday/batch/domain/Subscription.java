package com.devniceday.batch.domain;


public record Subscription(
        long id,
        long providerId,
        SubscriptionType type
) {
}

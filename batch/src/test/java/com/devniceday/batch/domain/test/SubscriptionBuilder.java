package com.devniceday.batch.domain.test;

import com.devniceday.batch.domain.FeedSubscription;
import com.devniceday.batch.domain.Subscription;
import com.devniceday.batch.domain.SubscriptionType;
import com.devniceday.batch.domain.WebListSubscription;
import com.devniceday.test.java.LongIncrementUtil;
import jakarta.annotation.Nullable;

public class SubscriptionBuilder {

    @Nullable
    private Long id;

    @Nullable
    private Long providerId;

    @Nullable
    private SubscriptionType type;

    @Nullable
    private FeedSubscription feed;

    @Nullable
    private WebListSubscription webList;

    public static Subscription createFeed() {
        return new SubscriptionBuilder().type(SubscriptionType.FEED).build();
    }

    public static Subscription createWebList() {
        return new SubscriptionBuilder().type(SubscriptionType.LIST_SCRAPPING).build();
    }

    public SubscriptionBuilder id(long id) {
        this.id = id;
        return this;
    }

    public SubscriptionBuilder providerId(long providerId) {
        this.providerId = providerId;
        return this;
    }

    public SubscriptionBuilder type(SubscriptionType type) {
        this.type = type;
        return this;
    }

    public SubscriptionBuilder feed(FeedSubscription feed) {
        this.feed = feed;
        return this;
    }

    public SubscriptionBuilder webList(WebListSubscription webList) {
        this.webList = webList;
        return this;
    }

    public Subscription build() {
        fillRequiredFields();
        return new Subscription(
                id,
                providerId,
                type,
                feed,
                webList
        );
    }

    private void fillRequiredFields() {
        if (id == null) {
            id = LongIncrementUtil.next();
        }
        if (providerId == null) {
            providerId = LongIncrementUtil.next();
        }
        if (type == null) {
            type = SubscriptionType.FEED;
        }
        if (feed == null && type == SubscriptionType.FEED) {
            feed = FeedSubscriptionBuilder.create();
        }
        if (webList == null && type == SubscriptionType.LIST_SCRAPPING) {
            webList = WebListSubscriptionBuilder.create();
        }
    }
}

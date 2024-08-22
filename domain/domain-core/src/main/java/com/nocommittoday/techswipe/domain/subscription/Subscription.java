package com.nocommittoday.techswipe.domain.subscription;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;

public abstract class Subscription {

    private final SubscriptionId id;

    private final SubscriptionType type;

    private final TechContentProviderId providerId;

    protected Subscription(SubscriptionId id, SubscriptionType type, TechContentProviderId providerId) {
        this.id = id;
        this.type = type;
        this.providerId = providerId;
    }

    public static ListScrappingSubscription createListScrapping(
            SubscriptionId id,
            TechContentProviderId providerId,
            ListScrapping listScrapping,
            ContentScrapping contentScrapping
    ) {
        return new ListScrappingSubscription(id, providerId, listScrapping, contentScrapping);
    }

    public static FeedSubscription createFeed(
            SubscriptionId id,
            TechContentProviderId providerId,
            String url,
            ContentScrapping contentScrapping
    ) {
        return new FeedSubscription(id, providerId, url, contentScrapping);
    }

    public abstract boolean isInitRequired();

    public SubscriptionId getId() {
        return id;
    }

    public SubscriptionType getType() {
        return type;
    }

    public TechContentProviderId getProviderId() {
        return providerId;
    }
}

package com.nocommittoday.techswipe.domain.subscription;

public abstract class Subscription {

    private final SubscriptionId id;

    private final SubscriptionType type;

    protected Subscription(SubscriptionId id, SubscriptionType type) {
        this.id = id;
        this.type = type;
    }

    public static ListScrappingSubscription createListScrapping(
            SubscriptionId id,
            ListScrapping listScrapping,
            ContentScrapping contentScrapping
    ) {
        return new ListScrappingSubscription(id, listScrapping, contentScrapping);
    }

    public static FeedSubscription createFeed(
            SubscriptionId id,
            String url,
            ContentScrapping contentScrapping
    ) {
        return new FeedSubscription(id, url, contentScrapping);
    }

    public abstract boolean isInitRequired();

    public SubscriptionId getId() {
        return id;
    }

    public SubscriptionType getType() {
        return type;
    }
}

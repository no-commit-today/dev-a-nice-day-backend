package com.nocommittoday.techswipe.domain.subscription;

public class FeedSubscription extends Subscription {

    private final String url;

    private final ContentScrapping contentScrapping;

    FeedSubscription(
            SubscriptionId id,
            String url,
            ContentScrapping contentScrapping
    ) {
        super(id, SubscriptionType.FEED);
        this.url = url;
        this.contentScrapping = contentScrapping;
    }

    @Override
    public boolean isInitRequired() {
        return false;
    }

    public String getUrl() {
        return url;
    }

    public ContentScrapping getContentScrapping() {
        return contentScrapping;
    }

}

package com.nocommittoday.techswipe.domain.subscription;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;

public class FeedSubscription extends Subscription {

    private final String url;

    private final ContentScrapping contentScrapping;

    FeedSubscription(
            SubscriptionId id,
            TechContentProviderId providerId,
            String url,
            ContentScrapping contentScrapping
    ) {
        super(id, SubscriptionType.FEED, providerId);
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

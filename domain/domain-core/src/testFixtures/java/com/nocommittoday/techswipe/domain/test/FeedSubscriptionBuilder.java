package com.nocommittoday.techswipe.domain.test;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.subscription.ContentScrapping;
import com.nocommittoday.techswipe.domain.subscription.FeedSubscription;
import com.nocommittoday.techswipe.domain.subscription.SubscriptionId;

import javax.annotation.Nullable;

public class FeedSubscriptionBuilder extends SubscriptionBuilder {

    @Nullable
    private String url;

    @Nullable
    private ContentScrapping contentScrapping;

    public FeedSubscriptionBuilder() {
    }

    public static FeedSubscription create() {
        return new FeedSubscriptionBuilder().build();
    }

    public FeedSubscriptionBuilder url(String url) {
        this.url = url;
        return this;
    }

    public FeedSubscriptionBuilder contentScrapping(ContentScrapping contentScrapping) {
        this.contentScrapping = contentScrapping;
        return this;
    }

    public FeedSubscription build() {
        fillRequiredFields();
        return FeedSubscription.createFeed(
                id,
                providerId,
                url,
                contentScrapping
        );
    }

    private void fillRequiredFields() {
        if (id == null) {
            id = new SubscriptionId(LocalAutoIncrementIdUtils.nextId());
        }
        if (providerId == null) {
            providerId = new TechContentProviderId(LocalAutoIncrementIdUtils.nextId());
        }
        if (url == null) {
            url = "url-" + id.value();
        }
        if (contentScrapping == null) {
            contentScrapping = ContentScrappingBuilder.create();
        }
    }
}

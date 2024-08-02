package com.nocommittoday.techswipe.subscription.domain;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;

import javax.annotation.Nullable;
import java.util.List;


public class Subscription {

    private final SubscriptionId id;

    private final TechContentProviderId providerId;

    private final SubscriptionType type;

    private final SubscriptionType initType;

    @Nullable
    private final String feedUrl;

    private final ContentCrawling contentCrawling;

    private final List<ListCrawling> listCrawlings;

    public Subscription(
            SubscriptionId id,
            TechContentProviderId providerId,
            SubscriptionType type,
            SubscriptionType initType,
            @Nullable String feedUrl,
            ContentCrawling contentCrawling,
            List<ListCrawling> listCrawlings
    ) {
        this.contentCrawling = contentCrawling;
        this.id = id;
        this.providerId = providerId;
        this.type = type;
        this.initType = initType;
        this.feedUrl = feedUrl;
        this.listCrawlings = listCrawlings;
    }

    public FeedSubscription toFeed() {
        return new FeedSubscription(
                feedUrl,
                contentCrawling
        );
    }

    public List<ListCrawlingSubscription> toListCrawlings() {
        return listCrawlings.stream()
                .map(listCrawling ->
                        new ListCrawlingSubscription(listCrawling, contentCrawling)
                ).toList();
    }

    public ContentCrawling getContentCrawling() {
        return contentCrawling;
    }

    @Nullable
    public String getFeedUrl() {
        return feedUrl;
    }

    public SubscriptionId getId() {
        return id;
    }

    public SubscriptionType getInitType() {
        return initType;
    }

    public List<ListCrawling> getListCrawlings() {
        return listCrawlings;
    }

    public TechContentProviderId getProviderId() {
        return providerId;
    }

    public SubscriptionType getType() {
        return type;
    }
}

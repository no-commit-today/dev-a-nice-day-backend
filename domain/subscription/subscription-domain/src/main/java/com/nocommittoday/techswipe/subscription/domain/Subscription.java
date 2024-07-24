package com.nocommittoday.techswipe.subscription.domain;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.List;


@RequiredArgsConstructor
@Getter
public class Subscription {

    private final SubscriptionId id;

    private final TechContentProviderId providerId;

    private final SubscriptionType type;

    private final SubscriptionType initType;

    @Nullable
    private final String feedUrl;

    private final ContentCrawling contentCrawling;

    private final List<ListCrawling> listCrawlings;

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
}

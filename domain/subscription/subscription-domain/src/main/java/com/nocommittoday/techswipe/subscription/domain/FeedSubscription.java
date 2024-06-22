package com.nocommittoday.techswipe.subscription.domain;

public record FeedSubscription(
        String url,
        ContentCrawling contentCrawling
) {
}

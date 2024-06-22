package com.nocommittoday.techswipe.subscription.domain;

import lombok.Builder;

@Builder
public record ListCrawlingSubscription(
        ListCrawling listCrawling,
        ContentCrawling contentCrawling
) {
}

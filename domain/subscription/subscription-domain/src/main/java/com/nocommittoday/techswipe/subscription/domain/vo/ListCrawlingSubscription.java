package com.nocommittoday.techswipe.subscription.domain.vo;

import lombok.Builder;

@Builder
public record ListCrawlingSubscription(
        ListCrawling listCrawling,
        ContentCrawling contentCrawling
) {
}

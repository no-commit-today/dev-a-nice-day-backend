package com.nocommittoday.techswipe.subscription.domain.vo;

import lombok.Builder;

@Builder
public record ListCrawlingSubscription(
        ContentCrawlingIndexes contentCrawlingIndexes,
        ListCrawling listCrawling
) {
}

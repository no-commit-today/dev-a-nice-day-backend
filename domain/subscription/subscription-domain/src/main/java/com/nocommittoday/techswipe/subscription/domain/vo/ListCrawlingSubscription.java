package com.nocommittoday.techswipe.subscription.domain.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record ListCrawlingSubscription(
        @NonNull ListCrawling listCrawling,
        @NonNull ContentCrawling contentCrawling
) {
}

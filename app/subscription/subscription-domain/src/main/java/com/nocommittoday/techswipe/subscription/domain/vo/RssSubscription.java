package com.nocommittoday.techswipe.subscription.domain.vo;

import lombok.NonNull;

import javax.annotation.Nullable;

public record RssSubscription(
        @NonNull String url,
        @Nullable ContentCrawlingIndexes contentCrawlingIndexes,
        @NonNull ContentCrawlingNeeds contentCrawlingNeeds
) {
}

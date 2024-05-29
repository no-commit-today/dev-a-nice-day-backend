package com.nocommittoday.techswipe.subscription.domain.vo;

import lombok.NonNull;

import javax.annotation.Nullable;

public record AtomSubscription(
        @NonNull String url,
        @Nullable ContentCrawlingIndexes contentCrawlingIndexes
) {
}

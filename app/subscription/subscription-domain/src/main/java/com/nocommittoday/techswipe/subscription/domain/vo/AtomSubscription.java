package com.nocommittoday.techswipe.subscription.domain.vo;

import lombok.NonNull;

public record AtomSubscription(
        @NonNull String url,
        @NonNull ContentCrawling contentCrawling
) {
}

package com.nocommittoday.techswipe.subscription.domain.vo;

import lombok.NonNull;

public record RssSubscription(
        @NonNull String url,
        @NonNull ContentCrawling contentCrawling
) {
}

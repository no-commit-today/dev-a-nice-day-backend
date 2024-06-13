package com.nocommittoday.techswipe.subscription.domain.vo;

import lombok.NonNull;

public record RssSubscription(
        String url,
        ContentCrawling contentCrawling
) {
}

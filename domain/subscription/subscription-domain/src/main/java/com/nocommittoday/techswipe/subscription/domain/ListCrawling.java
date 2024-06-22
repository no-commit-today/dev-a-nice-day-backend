package com.nocommittoday.techswipe.subscription.domain;

import javax.annotation.Nullable;

public record ListCrawling(
        String url,
        Crawling crawling,
        @Nullable String pageUrlFormat
) {
}

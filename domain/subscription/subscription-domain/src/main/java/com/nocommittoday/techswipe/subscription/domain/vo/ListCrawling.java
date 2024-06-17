package com.nocommittoday.techswipe.subscription.domain.vo;

import javax.annotation.Nullable;

public record ListCrawling(
        String url,
        Crawling crawling,
        @Nullable String pageUrlFormat
) {
}

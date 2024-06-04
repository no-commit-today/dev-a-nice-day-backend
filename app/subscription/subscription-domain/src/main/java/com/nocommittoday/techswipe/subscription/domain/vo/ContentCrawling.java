package com.nocommittoday.techswipe.subscription.domain.vo;

import javax.annotation.Nullable;

public record ContentCrawling(
        @Nullable Crawling title,
        @Nullable Crawling date,
        @Nullable Crawling content
) {
}

package com.nocommittoday.techswipe.subscription.domain;

import javax.annotation.Nullable;
import java.util.Objects;

public record ListCrawling(
        String url,
        Crawling crawling,
        @Nullable String pageUrlFormat
) {

    public static final String PAGE_URL_PAGE_PLACEHOLDER = "{page}";

    public boolean isPaginated() {
        return pageUrlFormat != null;
    }

    public String getPageUrl(final int page) {
        return Objects.requireNonNull(pageUrlFormat).replace(PAGE_URL_PAGE_PLACEHOLDER, String.valueOf(page));
    }
}

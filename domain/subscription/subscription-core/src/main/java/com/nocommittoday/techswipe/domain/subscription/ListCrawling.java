package com.nocommittoday.techswipe.domain.subscription;

import javax.annotation.Nullable;
import java.util.Objects;

public record ListCrawling(
        String url,
        Crawling crawling,
        @Nullable String pageUrlFormat,
        @Nullable String contentUrlFormat
) {

    public ListCrawling(String url, Crawling crawling) {
        this(url, crawling, null, null);
    }

    public ListCrawling(
            String url,
            Crawling crawling,
            @Nullable String pageUrlFormat
    ) {
        this(url, crawling, pageUrlFormat, null);
    }

    public static String PAGE_URL_PAGE_PLACEHOLDER = "{page}";

    public static String CONTENT_URL_ID_PLACEHOLDER = "{contentId}";

    public boolean isPaginated() {
        return pageUrlFormat != null;
    }

    public String getPageUrl(int page) {
        return Objects.requireNonNull(pageUrlFormat).replace(PAGE_URL_PAGE_PLACEHOLDER, String.valueOf(page));
    }

    public boolean isContentUrl(@Nullable String url) {
        if (url == null) {
            return false;
        }

        if (contentUrlFormat == null) {
            return true;
        }

        return url.matches(contentUrlFormat.replace(CONTENT_URL_ID_PLACEHOLDER, ".+"));
    }
}

package com.devniceday.batch.domain;

import javax.annotation.Nullable;
import java.util.Objects;

public record ListScrapping(
        String url,
        Scrapping scrapping,
        @Nullable String pageUrlFormat,
        @Nullable String contentUrlFormat
) {

    private static final String PAGE_URL_PAGE_PLACEHOLDER = "{page}";
    private static final String CONTENT_URL_ID_PLACEHOLDER = "{contentId}";

    public boolean isPaginated() {
        return pageUrlFormat != null;
    }

    public String getPageUrl(int page) {
        if (!isPaginated()) {
            throw new IllegalStateException("페이지네이션을 지원하지 않습니다. url: " + url);
        }

        return Objects.requireNonNull(pageUrlFormat)
                .replace(PAGE_URL_PAGE_PLACEHOLDER, String.valueOf(page));
    }

    public boolean isContentUrl(@Nullable String url) {
        if (url == null || url.isBlank()) {
            return false;
        }

        if (contentUrlFormat == null) {
            return true;
        }

        return url.matches(contentUrlFormat.replace(CONTENT_URL_ID_PLACEHOLDER, "[가-힣a-zA-Z0-9\\-_]+"));
    }
}

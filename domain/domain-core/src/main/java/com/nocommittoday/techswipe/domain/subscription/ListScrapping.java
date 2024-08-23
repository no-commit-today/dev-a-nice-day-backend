package com.nocommittoday.techswipe.domain.subscription;

import javax.annotation.Nullable;
import java.util.Objects;

public class ListScrapping {

    public static final String PAGE_URL_PAGE_PLACEHOLDER = "{page}";
    public static final String CONTENT_URL_ID_PLACEHOLDER = "{contentId}";

    private final String url;

    private final Scrapping scrapping;

    @Nullable
    private final String pageUrlFormat;

    @Nullable
    private final String contentUrlFormat;

    public ListScrapping(
            String url, Scrapping scrapping, @Nullable String pageUrlFormat, @Nullable String contentUrlFormat) {
        this.url = url;
        this.scrapping = scrapping;
        this.pageUrlFormat = pageUrlFormat;
        this.contentUrlFormat = contentUrlFormat;
    }

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

        return url.matches(contentUrlFormat.replace(CONTENT_URL_ID_PLACEHOLDER, ".+"));
    }

    public String getUrl() {
        return url;
    }

    public Scrapping getScrapping() {
        return scrapping;
    }
}
package com.devniceday.storage.db.core;

import javax.annotation.Nullable;

public class BatchSubscriptionListScrappingData {

    private String url;

    private BatchScrappingData scrapping;

    @Nullable
    private String pageUrlFormat;

    @Nullable
    private String contentUrlFormat;

    public BatchSubscriptionListScrappingData(String url, BatchScrappingData scrapping, @Nullable String pageUrlFormat, @Nullable String contentUrlFormat) {
        this.url = url;
        this.scrapping = scrapping;
        this.pageUrlFormat = pageUrlFormat;
        this.contentUrlFormat = contentUrlFormat;
    }

    public String getUrl() {
        return url;
    }

    public BatchScrappingData getScrapping() {
        return scrapping;
    }

    @Nullable
    public String getPageUrlFormat() {
        return pageUrlFormat;
    }

    @Nullable
    public String getContentUrlFormat() {
        return contentUrlFormat;
    }
}

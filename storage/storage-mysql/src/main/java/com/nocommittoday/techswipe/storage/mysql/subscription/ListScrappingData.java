package com.nocommittoday.techswipe.storage.mysql.subscription;

import com.nocommittoday.techswipe.domain.subscription.ListScrapping;

import javax.annotation.Nullable;

public class ListScrappingData {

    private String url;

    private ScrappingData scrapping;

    @Nullable
    private String pageUrlFormat;

    @Nullable
    private String contentUrlFormat;

    protected ListScrappingData() {
    }

    public ListScrappingData(String url, ScrappingData scrapping, @Nullable String pageUrlFormat, @Nullable String contentUrlFormat) {
        this.url = url;
        this.scrapping = scrapping;
        this.pageUrlFormat = pageUrlFormat;
        this.contentUrlFormat = contentUrlFormat;
    }

    public ListScrapping toDomain() {
        return new ListScrapping(
                url, scrapping.toDomain(), pageUrlFormat, contentUrlFormat
        );
    }

    public String getUrl() {
        return url;
    }

    public ScrappingData getScrapping() {
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

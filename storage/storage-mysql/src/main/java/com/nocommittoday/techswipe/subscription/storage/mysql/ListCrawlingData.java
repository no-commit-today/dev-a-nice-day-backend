package com.nocommittoday.techswipe.subscription.storage.mysql;

import com.nocommittoday.techswipe.subscription.domain.ListCrawling;

import javax.annotation.Nullable;

public class ListCrawlingData {

    private String url;

    private CrawlingData crawling;

    @Nullable
    private String pageUrlFormat;

    protected ListCrawlingData() {
    }

    public ListCrawlingData(String url, CrawlingData crawling, String pageUrlFormat) {
        this.url = url;
        this.crawling = crawling;
        this.pageUrlFormat = pageUrlFormat;
    }

    public static ListCrawlingData from(ListCrawling listCrawling) {
        return new ListCrawlingData(
                listCrawling.url(),
                CrawlingData.from(listCrawling.crawling()),
                listCrawling.pageUrlFormat()
        );
    }

    public ListCrawling toDomain() {
        return new ListCrawling(
                url,
                crawling.toDomain(),
                pageUrlFormat
        );
    }

    public CrawlingData getCrawling() {
        return crawling;
    }

    @Nullable
    public String getPageUrlFormat() {
        return pageUrlFormat;
    }

    public String getUrl() {
        return url;
    }
}

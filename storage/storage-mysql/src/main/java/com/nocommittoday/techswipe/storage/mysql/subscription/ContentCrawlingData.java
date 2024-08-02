package com.nocommittoday.techswipe.storage.mysql.subscription;

import com.nocommittoday.techswipe.subscription.domain.ContentCrawling;

public class ContentCrawlingData {

    private CrawlingData title;

    private CrawlingData date;

    private CrawlingData content;

    protected ContentCrawlingData() {
    }

    public ContentCrawlingData(CrawlingData title, CrawlingData date, CrawlingData content) {
        this.title = title;
        this.date = date;
        this.content = content;
    }

    public static ContentCrawlingData from(ContentCrawling contentCrawling) {
        return new ContentCrawlingData(
                CrawlingData.from(contentCrawling.title()),
                CrawlingData.from(contentCrawling.date()),
                CrawlingData.from(contentCrawling.content())
        );
    }

    public ContentCrawling toDomain() {
        return new ContentCrawling(
                title.toDomain(),
                date.toDomain(),
                content.toDomain()
        );
    }

    public CrawlingData getContent() {
        return content;
    }

    public CrawlingData getDate() {
        return date;
    }

    public CrawlingData getTitle() {
        return title;
    }
}

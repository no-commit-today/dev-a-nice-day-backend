package com.nocommittoday.techswipe.subscription.storage.mysql;

import com.nocommittoday.techswipe.subscription.domain.ContentCrawling;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentCrawlingData {

    private CrawlingData title;

    private CrawlingData date;

    private CrawlingData content;

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
}

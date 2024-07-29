package com.nocommittoday.techswipe.subscription.storage.mysql;

import com.nocommittoday.techswipe.subscription.domain.ListCrawling;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ListCrawlingData {

    private String url;

    private CrawlingData crawling;

    @Nullable
    private String pageUrlFormat;

    public static ListCrawlingData from(final ListCrawling listCrawling) {
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
}

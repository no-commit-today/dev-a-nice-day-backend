package com.nocommittoday.techswipe.admin.controller;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionRegister;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionType;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

import java.util.List;

public record SubscriptionRegisterRequest(
        @NotNull SubscriptionType type,
        @NotNull SubscriptionType initType,
        @URL String feedUrl,
        @NotNull ContentCrawling contentCrawling,
        @NotNull List<ListCrawling> listCrawlings
) {

    record ContentCrawling(
            @NotNull Crawling title,
            @NotNull Crawling date,
            @NotNull Crawling content
    ) {
    }

    record ListCrawling(
            @URL String url,
            @NotNull Crawling crawling,
            @URL String pageUrlFormat
    ) {
    }

    record Crawling(
            @NotNull CrawlingType type,
            String selector,
            List<Integer> indexes
    ) {
    }

    public SubscriptionRegister toDomain(TechContentProviderId providerId) {
        return new SubscriptionRegister(
                providerId,
                type,
                initType,
                feedUrl,
                new com.nocommittoday.techswipe.subscription.domain.ContentCrawling(
                        new com.nocommittoday.techswipe.subscription.domain.Crawling(
                                contentCrawling.title().type(),
                                contentCrawling.title().selector(),
                                contentCrawling.title().indexes()
                        ),
                        new com.nocommittoday.techswipe.subscription.domain.Crawling(
                                contentCrawling.date().type(),
                                contentCrawling.date().selector(),
                                contentCrawling.date().indexes()
                        ),
                        new com.nocommittoday.techswipe.subscription.domain.Crawling(
                                contentCrawling.content().type(),
                                contentCrawling.content().selector(),
                                contentCrawling.content().indexes()
                        )
                ),
                listCrawlings.stream().map(item -> new com.nocommittoday.techswipe.subscription.domain.ListCrawling(
                        item.url(),
                        new com.nocommittoday.techswipe.subscription.domain.Crawling(
                                item.crawling().type(),
                                item.crawling().selector(),
                                item.crawling().indexes()
                        ),
                        item.pageUrlFormat()

                )).toList()
        );
    }
}

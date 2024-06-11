package com.nocommittoday.techswipe.subscription.controller.admin.request;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.subscription.domain.enums.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.enums.SubscriptionType;
import com.nocommittoday.techswipe.subscription.domain.vo.SubscriptionRegister;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.URL;

import java.util.List;

public record SubscriptionRegisterRequest(
        @NotNull @Positive Long providerId,
        @NotNull SubscriptionType type,
        @URL String rssUrl,
        @URL String atomUrl,
        @NotNull ContentCrawling contentCrawling,
        @NotNull List<ListCrawling> listCrawlings
) {

    record ContentCrawling(
            Crawling title,
            Crawling date,
            Crawling content
    ) {
    }

    record ListCrawling(
            @URL String url,
            Crawling crawling,
            @URL String pageUrlFormat
    ) {
    }

    record Crawling(
            @NotNull CrawlingType type,
            String selector,
            List<Integer> indexes
    ) {
    }

    public SubscriptionRegister toDomain() {
        return new SubscriptionRegister(
                new TechContentProvider.TechContentProviderId(providerId),
                type,
                rssUrl,
                atomUrl,
                new com.nocommittoday.techswipe.subscription.domain.vo.ContentCrawling(
                        new com.nocommittoday.techswipe.subscription.domain.vo.Crawling(
                                contentCrawling.title().type(),
                                contentCrawling.title().selector(),
                                contentCrawling.title().indexes()
                        ),
                        new com.nocommittoday.techswipe.subscription.domain.vo.Crawling(
                                contentCrawling.date().type(),
                                contentCrawling.date().selector(),
                                contentCrawling.date().indexes()
                        ),
                        new com.nocommittoday.techswipe.subscription.domain.vo.Crawling(
                                contentCrawling.content().type(),
                                contentCrawling.content().selector(),
                                contentCrawling.content().indexes()
                        )
                ),
                listCrawlings.stream().map(item -> new com.nocommittoday.techswipe.subscription.domain.vo.ListCrawling(
                        item.url(),
                        new com.nocommittoday.techswipe.subscription.domain.vo.Crawling(
                                item.crawling().type(),
                                item.crawling().selector(),
                                item.crawling().indexes()
                        ),
                        item.pageUrlFormat()

                )).toList()
        );
    }
}

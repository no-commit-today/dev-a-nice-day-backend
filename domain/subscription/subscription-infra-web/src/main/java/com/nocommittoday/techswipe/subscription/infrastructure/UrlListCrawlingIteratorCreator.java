package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.subscription.domain.vo.ListCrawling;
import org.springframework.stereotype.Component;

@Component
public class UrlListCrawlingIteratorCreator {

    public UrlListCrawlingIterator create(
            final DocumentConnector documentConnector,
            final ListCrawling listCrawling
    ) {
        return new UrlListCrawlingIterator(
                documentConnector,
                listCrawling.crawling(),
                listCrawling.url(),
                listCrawling.pageUrlFormat()
        );
    }
}

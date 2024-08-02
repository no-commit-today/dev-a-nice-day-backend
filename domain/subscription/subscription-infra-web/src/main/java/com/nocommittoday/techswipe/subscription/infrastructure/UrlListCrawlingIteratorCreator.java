package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.subscription.domain.ListCrawling;
import org.springframework.stereotype.Component;

@Component
public class UrlListCrawlingIteratorCreator {

    private final DocumentConnector documentConnector;

    public UrlListCrawlingIteratorCreator(DocumentConnector documentConnector) {
        this.documentConnector = documentConnector;
    }

    public UrlListCrawlingIterator create(ListCrawling listCrawling) {
        return new UrlListCrawlingIterator(
                documentConnector,
                listCrawling
        );
    }
}

package com.nocommittoday.techswipe.domain.subscription;

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

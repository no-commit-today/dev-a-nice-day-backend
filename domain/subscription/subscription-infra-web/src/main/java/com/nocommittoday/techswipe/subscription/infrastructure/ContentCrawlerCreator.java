package com.nocommittoday.techswipe.subscription.infrastructure;

import org.springframework.stereotype.Component;

@Component
public class ContentCrawlerCreator {

    public ContentCrawler create(
            final DocumentElementExtractor documentElementExtractor,
            final DocumentConnector documentConnector, final String url
    ) {
        return new ContentCrawler(documentElementExtractor, documentConnector, url);
    }
}

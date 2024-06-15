package com.nocommittoday.techswipe.subscription.infrastructure;

import org.springframework.stereotype.Component;

@Component
public class ContentCrawlerCreator {

    public ContentCrawler create(
            final DocumentConnector documentConnector, final String url
    ) {
        return new ContentCrawler(documentConnector, url);
    }
}

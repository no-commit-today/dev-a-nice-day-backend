package com.nocommittoday.techswipe.subscription.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContentCrawlerCreator {

    private final DocumentElementExtractor documentElementExtractor;

    private final DocumentConnector documentConnector;

    private final HtmlTagCleaner htmlTagCleaner;

    public ContentCrawler create(final String url) {
        return new ContentCrawler(documentElementExtractor, documentConnector, url, htmlTagCleaner);
    }
}

package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.subscription.domain.ListCrawling;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrlListCrawlingIteratorCreator {

    private final DocumentConnector documentConnector;

    public UrlListCrawlingIterator create(final ListCrawling listCrawling) {
        return new UrlListCrawlingIterator(
                documentConnector,
                listCrawling
        );
    }
}

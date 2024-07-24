package com.nocommittoday.techswipe.subscription.infrastructure.dev;

import com.nocommittoday.techswipe.subscription.domain.Crawling;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.ListCrawling;
import com.nocommittoday.techswipe.subscription.infrastructure.DocumentConnector;
import com.nocommittoday.techswipe.subscription.infrastructure.DocumentElementExtractor;
import com.nocommittoday.techswipe.subscription.infrastructure.UrlListCrawlingIterator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Tag("develop")
class UrlListCrawlingIteratorDevTest {

    private DocumentElementExtractor documentElementExtractor = new DocumentElementExtractor();

    private DocumentConnector documentConnector = new DocumentConnector();

    @Test
    void 토스() {
        final UrlListCrawlingIterator iterator = new UrlListCrawlingIterator(
                documentConnector,
                documentElementExtractor,
                new ListCrawling(
                        "https://toss.tech/tech",
                        new Crawling(
                                CrawlingType.INDEX,
                                null,
                                List.of(0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0)
                        ),
                        null)
        );

        List<String> urls = new ArrayList<>();
        while (iterator.hasNext()) {
            urls.add(iterator.next());
        }

        System.out.println(urls);
    }
}

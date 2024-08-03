package com.nocommittoday.techswipe.domain.subscription.dev;

import com.nocommittoday.techswipe.domain.subscription.Crawling;
import com.nocommittoday.techswipe.domain.subscription.CrawlingType;
import com.nocommittoday.techswipe.domain.subscription.ListCrawling;
import com.nocommittoday.techswipe.domain.subscription.DocumentConnector;
import com.nocommittoday.techswipe.domain.subscription.UrlListCrawlingIterator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Tag("develop")
class UrlListCrawlingIteratorDevTest {

    private DocumentConnector documentConnector = new DocumentConnector();

    @Test
    void 토스() {
        UrlListCrawlingIterator iterator = new UrlListCrawlingIterator(
                documentConnector,
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

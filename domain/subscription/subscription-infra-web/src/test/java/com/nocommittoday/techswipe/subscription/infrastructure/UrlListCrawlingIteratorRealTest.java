package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.subscription.domain.Crawling;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Disabled
class UrlListCrawlingIteratorRealTest {

    @Autowired
    private DocumentConnector documentConnector;

    @Autowired
    private DocumentElementExtractor documentElementExtractor;

    @Test
    void 토스() {
        final UrlListCrawlingIterator iterator = new UrlListCrawlingIterator(
                documentConnector,
                documentElementExtractor,
                new Crawling(
                        CrawlingType.INDEX,
                        null,
                        List.of(0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0)
                ),
                "https://toss.tech/tech",
                null
        );

        List<String> urls = new ArrayList<>();
        while (iterator.hasNext()) {
            urls.add(iterator.next());
        }

        System.out.println(urls);
    }
}

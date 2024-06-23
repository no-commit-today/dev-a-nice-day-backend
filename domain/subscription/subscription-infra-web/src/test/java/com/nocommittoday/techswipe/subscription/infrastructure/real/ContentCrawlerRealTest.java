package com.nocommittoday.techswipe.subscription.infrastructure.real;


import com.nocommittoday.techswipe.core.infrastructure.LocalDateHolder;
import com.nocommittoday.techswipe.subscription.domain.Crawling;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;
import com.nocommittoday.techswipe.subscription.infrastructure.ContentCrawler;
import com.nocommittoday.techswipe.subscription.infrastructure.DocumentConnector;
import com.nocommittoday.techswipe.subscription.infrastructure.DocumentElementExtractor;
import com.nocommittoday.techswipe.subscription.infrastructure.LocalDateParser;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class ContentCrawlerRealTest {

    private DocumentElementExtractor documentElementExtractor = new DocumentElementExtractor();

    private DocumentConnector documentConnector = new DocumentConnector();

    private LocalDateParser localDateParser = new LocalDateParser(new LocalDateHolder());

    @Test
    void 토스() {
        final ContentCrawler contentCrawler = new ContentCrawler(
                documentElementExtractor,
                documentConnector,
                "https://toss.tech/article/secure-efficient-ai"
        );

        final String imageUrl = contentCrawler.getImageUrl();
        System.out.println("imageUrl = " + imageUrl);

        final String title = contentCrawler.getText(new Crawling(
                CrawlingType.INDEX,
                null,
                List.of(0, 0, 1, 0, 1, 0, 0, 1)
        ));
        System.out.println("title = " + title);

        final LocalDate date = localDateParser.parse(contentCrawler.getText(new Crawling(
                CrawlingType.INDEX,
                null,
                List.of(0, 0, 1, 0, 1, 0, 0, 3, 0, 1)
        )));
        System.out.println("date = " + date);

        final String content = contentCrawler.get(new Crawling(
                CrawlingType.INDEX,
                null,
                List.of(0, 0, 1, 0, 1, 0, 1)
        ));
        System.out.println("content = \n" + content);
    }
}

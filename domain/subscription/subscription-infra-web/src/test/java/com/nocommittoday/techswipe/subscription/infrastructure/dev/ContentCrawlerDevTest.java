package com.nocommittoday.techswipe.subscription.infrastructure.dev;


import com.nocommittoday.techswipe.core.infrastructure.LocalDateHolder;
import com.nocommittoday.techswipe.subscription.domain.Crawling;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;
import com.nocommittoday.techswipe.subscription.infrastructure.ContentCrawler;
import com.nocommittoday.techswipe.subscription.infrastructure.DocumentConnector;
import com.nocommittoday.techswipe.subscription.infrastructure.DocumentElementExtractor;
import com.nocommittoday.techswipe.subscription.infrastructure.LocalDateParser;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

@Tag("develop")
class ContentCrawlerDevTest {

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
                CrawlingType.SELECTOR,
                "div#__next > div > div.p-container > div.p-container__inner > div > article > header > h1",
                null
        ));
        System.out.println("title = " + title);

        final LocalDate date = localDateParser.parse(contentCrawler.getText(new Crawling(
                CrawlingType.SELECTOR,
                "div#__next > div > div.p-container > div.p-container__inner > div > article > header > section.author-container > div > div.css-154r2lc",
                null
        )));
        System.out.println("date = " + date);

        final String content = contentCrawler.get(new Crawling(
                CrawlingType.SELECTOR,
                "div#__next > div > div.p-container > div.p-container__inner > div > article > div.css-1vn47db",
                null
        ));
        System.out.println("content = \n" + content);
    }
}

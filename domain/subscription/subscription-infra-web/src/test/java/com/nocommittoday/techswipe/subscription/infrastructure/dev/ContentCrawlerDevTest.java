package com.nocommittoday.techswipe.subscription.infrastructure.dev;


import com.nocommittoday.techswipe.subscription.domain.Crawling;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;
import com.nocommittoday.techswipe.subscription.infrastructure.ContentCrawler;
import com.nocommittoday.techswipe.subscription.infrastructure.ContentCrawlerCreator;
import com.nocommittoday.techswipe.subscription.infrastructure.LocalDateParser;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("develop")
@SpringBootTest
class ContentCrawlerDevTest {

    @Autowired
    private ContentCrawlerCreator contentCrawlerCreator;

    @Autowired
    private LocalDateParser localDateParser;

    @Test
    void 토스() {
        final ContentCrawler contentCrawler = contentCrawlerCreator.create("https://toss.tech/article/secure-efficient-ai");

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

    @Test
    void 마켓컬리() {
        final ContentCrawler contentCrawler = contentCrawlerCreator.create("https://helloworld.kurly.com/blog/commit-mvcc-set-autocommit/");

        final String imageUrl = contentCrawler.getImageUrl();
        System.out.println("imageUrl = " + imageUrl);
        assertThat(imageUrl).isNotEmpty();

        final String content = contentCrawler.getCleaned(new Crawling(
                CrawlingType.INDEX,
                null,
                List.of(1, 0, 0)
        ));
        System.out.println("content = " + content);
        assertThat(content).isNotEmpty();
    }
}

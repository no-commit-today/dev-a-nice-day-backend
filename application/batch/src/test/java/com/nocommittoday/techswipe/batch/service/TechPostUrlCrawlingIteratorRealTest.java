package com.nocommittoday.techswipe.batch.service;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Tag("real")
class TechPostUrlCrawlingIteratorRealTest {

    @Test
    void 토스() {
        final TechPostUrlCrawlingIterator iterator = new TechPostUrlCrawlingIterator(
                "div#__next > div.p-container > div.p-container__inner > div > ul",
                "https://toss.tech/tech",
                null
        );
        final List<String> urlList = new ArrayList<>();
        while (iterator.hasNext()) {
            urlList.add(iterator.next());
        }
        System.out.println(urlList);
    }

    @Test
    void 리디() {
        final TechPostUrlCrawlingIterator iterator = new TechPostUrlCrawlingIterator(
                "div#page > main#primary > div.inner > ul",
                "https://ridicorp.com/story-category/tech-blog/",
                "https://ridicorp.com/story-category/tech-blog/page/%d/"
        );
        List<String> urlList = new ArrayList<>();
        while (iterator.hasNext()) {
            urlList.add(iterator.next());
        }
        System.out.println(urlList);
    }
}

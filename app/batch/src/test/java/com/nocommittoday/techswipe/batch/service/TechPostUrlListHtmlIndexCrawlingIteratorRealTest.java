package com.nocommittoday.techswipe.batch.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Tag("real")
@Disabled
class TechPostUrlListHtmlIndexCrawlingIteratorRealTest {

    @Test
    void 리디() {
        final TechPostUrlListHtmlIndexCrawlingIterator iterator = new TechPostUrlListHtmlIndexCrawlingIterator(
                List.of(11, 4, 1, 0),
                "https://ridicorp.com/story-category/tech-blog/",
                "https://ridicorp.com/story-category/tech-blog/page/%d/"
        );
        final List<String> urlList = new ArrayList<>();
        while (iterator.hasNext()) {
            urlList.add(iterator.next());
        }
        System.out.println(urlList);
    }
}
package com.nocommittoday.techswipe.subscription.adapter.out.web;

import com.nocommittoday.techswipe.subscription.application.port.out.ListCrawlingContentReaderPort;
import com.nocommittoday.techswipe.subscription.application.port.vo.SubscribedContent;
import com.nocommittoday.techswipe.subscription.domain.vo.ContentCrawlingIndexes;
import com.nocommittoday.techswipe.subscription.domain.vo.ListCrawling;
import com.nocommittoday.techswipe.subscription.domain.vo.ListCrawlingSubscription;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
class ListCrawlingContentReaderAdapter implements ListCrawlingContentReaderPort {

    @Override
    public List<SubscribedContent> getList(final ListCrawlingSubscription subscription, final LocalDate date) {
        final ListCrawling listCrawling = subscription.listCrawling();
        final ContentCrawlingIndexes contentCrawlingIndexes = subscription.contentCrawlingIndexes();
        final IndexUrlListCrawlingIterator iterator = new IndexUrlListCrawlingIterator(
                listCrawling.indexes(),
                listCrawling.url(),
                listCrawling.pageUrlFormat()
        );
        final List<SubscribedContent> result = new ArrayList<>();
        while (iterator.hasNext()) {
            final String url = iterator.next();
            final IndexContentCrawler crawler = new IndexContentCrawler(url);
            final String title = crawler.getTitle(contentCrawlingIndexes.title());
            final LocalDate publishedDate = crawler.getDate(Objects.requireNonNull(contentCrawlingIndexes.date()));
            final String imageUrl = crawler.getImageUrl();
            final String content = crawler.getContent(Objects.requireNonNull(contentCrawlingIndexes.content()));
            result.add(new SubscribedContent(
                    url,
                    title,
                    imageUrl,
                    publishedDate,
                    content
            ));
        }
        return Collections.unmodifiableList(result);
    }
}

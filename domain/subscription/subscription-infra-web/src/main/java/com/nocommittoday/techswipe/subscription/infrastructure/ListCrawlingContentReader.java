package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.subscription.domain.vo.ContentCrawling;
import com.nocommittoday.techswipe.subscription.domain.vo.ListCrawling;
import com.nocommittoday.techswipe.subscription.domain.vo.ListCrawlingSubscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ListCrawlingContentReader {

    private final DocumentConnector documentConnector;

    public List<SubscribedContent> getList(final ListCrawlingSubscription subscription, final LocalDate date) {
        final ListCrawling listCrawling = subscription.listCrawling();
        final ContentCrawling contentCrawling = subscription.contentCrawling();
        final UrlListCrawlingIterator iterator = new UrlListCrawlingIterator(
                documentConnector,
                listCrawling.crawling(),
                listCrawling.url(),
                listCrawling.pageUrlFormat()
        );
        final List<SubscribedContent> result = new ArrayList<>();
        while (iterator.hasNext()) {
            final String url = iterator.next();
            final ContentCrawler crawler = new ContentCrawler(documentConnector, url);
            final LocalDate publishedDate = crawler.getDate(Objects.requireNonNull(contentCrawling.date()));
            if (date.isAfter(publishedDate)) {
                break;
            }

            final String title = crawler.getTitle(Objects.requireNonNull(contentCrawling.title()));
            final String imageUrl = crawler.getImageUrl();
            final String content = crawler.getContent(Objects.requireNonNull(contentCrawling.content()));
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

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

@Component
@RequiredArgsConstructor
public class ListCrawlingContentReader {

    private final DocumentConnector documentConnector;
    private final UrlListCrawlingIteratorCreator urlListCrawlingIteratorCreator;
    private final ContentCrawlerCreator contentCrawlerCreator;
    private final LocalDateParser localDateParser;

    public List<SubscribedContent> getList(final ListCrawlingSubscription subscription, final LocalDate date) {
        final ListCrawling listCrawling = subscription.listCrawling();
        final ContentCrawling contentCrawling = subscription.contentCrawling();
        final UrlListCrawlingIterator iterator = urlListCrawlingIteratorCreator.create(documentConnector, listCrawling);
        final List<SubscribedContent> result = new ArrayList<>();
        while (iterator.hasNext()) {
            final String url = iterator.next();
            final ContentCrawler crawler = contentCrawlerCreator.create(documentConnector, url);
            final LocalDate publishedDate = localDateParser.parse(
                    crawler.getText(contentCrawling.date()));
            if (date.isAfter(publishedDate)) {
                break;
            }

            final String title = crawler.getText(contentCrawling.title());
            final String imageUrl = crawler.getImageUrl();
            final String content = crawler.get(contentCrawling.content());
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

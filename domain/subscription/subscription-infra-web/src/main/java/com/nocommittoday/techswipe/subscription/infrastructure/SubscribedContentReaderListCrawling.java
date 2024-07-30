package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.subscription.domain.ContentCrawling;
import com.nocommittoday.techswipe.subscription.domain.ListCrawling;
import com.nocommittoday.techswipe.subscription.domain.ListCrawlingSubscription;
import com.nocommittoday.techswipe.subscription.domain.SubscribedContent;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubscribedContentReaderListCrawling implements SubscribedContentReader {

    private final UrlListCrawlingIteratorCreator urlListCrawlingIteratorCreator;
    private final DocumentConnector documentConnector;
    private final LocalDateParser localDateParser;
    private final HtmlTagCleaner htmlTagCleaner;

    @Override
    public List<SubscribedContent> getList(Subscription subscription, LocalDate date) {
        return subscription.toListCrawlings().stream()
                .map(listCrawling -> getList(listCrawling, date))
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public boolean supports(Subscription subscription) {
        return SubscriptionType.LIST_CRAWLING == subscription.getType();
    }

    @Override
    public boolean supportsInit(Subscription subscription) {
        return SubscriptionType.LIST_CRAWLING == subscription.getInitType();
    }

    public List<SubscribedContent> getList(
            ListCrawlingSubscription subscription, LocalDate date) {
        ListCrawling listCrawling = subscription.listCrawling();
        ContentCrawling contentCrawling = subscription.contentCrawling();
        UrlListCrawlingIterator iterator = urlListCrawlingIteratorCreator.create(listCrawling);
        List<SubscribedContent> result = new ArrayList<>();

        while (iterator.hasNext()) {
            String url = iterator.next();
            DocumentCrawler documentCrawler = documentConnector.connect(url).get();
            LocalDate publishedDate = localDateParser.parse(
                    documentCrawler.getText(contentCrawling.date()));
            if (date.isAfter(publishedDate)) {
                break;
            }

            String title = documentCrawler.getText(contentCrawling.title());
            String imageUrl = documentCrawler.getImageUrl();
            String content = htmlTagCleaner.clean(documentCrawler.get(contentCrawling.content()));
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

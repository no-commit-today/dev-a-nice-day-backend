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
    public List<SubscribedContent> getList(final Subscription subscription, final LocalDate date) {
        return subscription.toListCrawlings().stream()
                .map(listCrawling -> getList(listCrawling, date))
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public boolean supports(final Subscription subscription) {
        return SubscriptionType.LIST_CRAWLING == subscription.getType();
    }

    @Override
    public boolean supportsInit(final Subscription subscription) {
        return SubscriptionType.LIST_CRAWLING == subscription.getInitType();
    }

    public List<SubscribedContent> getList(
            final ListCrawlingSubscription subscription, final LocalDate date) {
        final ListCrawling listCrawling = subscription.listCrawling();
        final ContentCrawling contentCrawling = subscription.contentCrawling();
        final UrlListCrawlingIterator iterator = urlListCrawlingIteratorCreator.create(listCrawling);
        final List<SubscribedContent> result = new ArrayList<>();

        while (iterator.hasNext()) {
            final String url = iterator.next();
            final DocumentCrawler documentCrawler = documentConnector.connect(url).get();
            final LocalDate publishedDate = localDateParser.parse(
                    documentCrawler.getText(contentCrawling.date()));
            if (date.isAfter(publishedDate)) {
                break;
            }

            final String title = documentCrawler.getText(contentCrawling.title());
            final String imageUrl = documentCrawler.getImageUrl();
            final String content = htmlTagCleaner.clean(documentCrawler.get(contentCrawling.content()));
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

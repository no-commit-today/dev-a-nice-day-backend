package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.subscription.domain.ContentCrawling;
import com.nocommittoday.techswipe.subscription.domain.ListCrawling;
import com.nocommittoday.techswipe.subscription.domain.ListCrawlingSubscription;
import com.nocommittoday.techswipe.subscription.domain.SubscribedContentResult;
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
public class ListCrawlingContentReader implements SubscribedContentReader {

    private final DocumentConnector documentConnector;
    private final DocumentElementExtractor documentElementExtractor;
    private final UrlListCrawlingIteratorCreator urlListCrawlingIteratorCreator;
    private final ContentCrawlerCreator contentCrawlerCreator;
    private final LocalDateParser localDateParser;

    @Override
    public List<SubscribedContentResult> getList(final Subscription subscription, final LocalDate date) {
        return subscription.toListCrawling().stream()
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

    public List<SubscribedContentResult> getList(final ListCrawlingSubscription subscription, final LocalDate date) {
        final ListCrawling listCrawling = subscription.listCrawling();
        final ContentCrawling contentCrawling = subscription.contentCrawling();
        final UrlListCrawlingIterator iterator = urlListCrawlingIteratorCreator.create(
                documentConnector,
                documentElementExtractor,
                listCrawling);
        final List<SubscribedContentResult> result = new ArrayList<>();

        while (iterator.hasNext()) {
            final String url = iterator.next();
            try {
                final ContentCrawler crawler = contentCrawlerCreator.create(url);
                final LocalDate publishedDate = localDateParser.parse(
                        crawler.getText(contentCrawling.date()));
                if (date.isAfter(publishedDate)) {
                    break;
                }

                final String title = crawler.getText(contentCrawling.title());
                final String imageUrl = crawler.getImageUrl();
                final String content = crawler.getCleaned(contentCrawling.content());
                result.add(SubscribedContentResult.ok(new SubscribedContentResult.Content(
                        url,
                        title,
                        imageUrl,
                        publishedDate,
                        content
                )));
            } catch (final Exception ex) {
                result.add(SubscribedContentResult.fail(url, ex));
            }
        }
        return Collections.unmodifiableList(result);
    }
}

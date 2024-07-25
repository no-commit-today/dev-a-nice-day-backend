package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.client.feed.FeedClient;
import com.nocommittoday.techswipe.client.feed.FeedResponse;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.FeedSubscription;
import com.nocommittoday.techswipe.subscription.domain.SubscribedContent;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SubscribedContentReaderFeed implements SubscribedContentReader {

    private final FeedClient feedClient;
    private final DocumentConnector documentConnector;
    private final LocalDateParser localDateParser;
    private final HtmlTagCleaner htmlTagCleaner;

    @Override
    public List<SubscribedContent> getList(final Subscription subscription, final LocalDate date) {
        return getList(subscription.toFeed(), date);
    }

    @Override
    public boolean supports(final Subscription subscription) {
        return SubscriptionType.FEED == subscription.getType();
    }

    @Override
    public boolean supportsInit(final Subscription subscription) {
        return SubscriptionType.FEED == subscription.getInitType();
    }

    public List<SubscribedContent> getList(
            final FeedSubscription subscription,
            final LocalDate date
    ) {
        final FeedResponse feed = feedClient.get(subscription.url()).get();
        final List<SubscribedContent> result = new ArrayList<>();

        for (FeedResponse.Entry entry : feed.entries()) {
            final DocumentCrawler documentCrawler = documentConnector.connect(entry.link()).get();
            final LocalDate publishedDate = Optional.of(subscription.contentCrawling().date())
                    .filter(contentCrawling -> CrawlingType.NONE != contentCrawling.type())
                    .map(documentCrawler::getText)
                    .map(localDateParser::parse)
                    .orElse(entry.date());
            if (date.isAfter(publishedDate)) {
                break;
            }
            final String imageUrl = documentCrawler.getImageUrl();
            final String title = Optional.of(subscription.contentCrawling().title())
                    .filter(contentCrawling -> CrawlingType.NONE != contentCrawling.type())
                    .map(documentCrawler::getText)
                    .orElse(entry.title());
            final String content = Optional.of(subscription.contentCrawling().content())
                    .filter(contentCrawling -> CrawlingType.NONE != contentCrawling.type())
                    .map(documentCrawler::get)
                    .map(htmlTagCleaner::clean)
                    .orElseGet(() -> htmlTagCleaner.clean(entry.content()));

            result.add(new SubscribedContent(
                    entry.link(),
                    title,
                    imageUrl,
                    publishedDate,
                    content
            ));
        }

        return Collections.unmodifiableList(result);
    }
}

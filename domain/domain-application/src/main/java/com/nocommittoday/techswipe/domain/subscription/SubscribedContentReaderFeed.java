package com.nocommittoday.techswipe.domain.subscription;

import com.nocommittoday.techswipe.client.feed.FeedClient;
import com.nocommittoday.techswipe.client.feed.FeedResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class SubscribedContentReaderFeed implements SubscribedContentReader {

    private final FeedClient feedClient;
    private final DocumentConnector documentConnector;
    private final LocalDateParser localDateParser;
    private final HtmlTagCleaner htmlTagCleaner;

    public SubscribedContentReaderFeed(
            FeedClient feedClient,
            DocumentConnector documentConnector,
            LocalDateParser localDateParser,
            HtmlTagCleaner htmlTagCleaner
    ) {
        this.feedClient = feedClient;
        this.documentConnector = documentConnector;
        this.localDateParser = localDateParser;
        this.htmlTagCleaner = htmlTagCleaner;
    }

    @Override
    public List<SubscribedContent> getList(Subscription subscription, LocalDate date) {
        return getList(subscription.toFeed(), date);
    }

    @Override
    public boolean supports(Subscription subscription) {
        return SubscriptionType.FEED == subscription.getType();
    }

    @Override
    public boolean supportsInit(Subscription subscription) {
        return SubscriptionType.FEED == subscription.getInitType();
    }

    public List<SubscribedContent> getList(
            FeedSubscription subscription,
            LocalDate date
    ) {
        FeedResponse feed = feedClient.get(subscription.url()).get();
        List<SubscribedContent> result = new ArrayList<>();

        for (FeedResponse.Entry entry : feed.entries()) {
            DocumentCrawler documentCrawler = documentConnector.connect(entry.link()).get();
            LocalDate publishedDate = Optional.of(subscription.contentCrawling().date())
                    .filter(contentCrawling -> CrawlingType.NONE != contentCrawling.type())
                    .map(documentCrawler::getText)
                    .map(localDateParser::parse)
                    .orElse(entry.date());
            if (date.isAfter(publishedDate)) {
                break;
            }
            String imageUrl = documentCrawler.getImageUrl();
            String title = Optional.of(subscription.contentCrawling().title())
                    .filter(contentCrawling -> CrawlingType.NONE != contentCrawling.type())
                    .map(documentCrawler::getText)
                    .orElse(entry.title());
            String content = Optional.of(subscription.contentCrawling().content())
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

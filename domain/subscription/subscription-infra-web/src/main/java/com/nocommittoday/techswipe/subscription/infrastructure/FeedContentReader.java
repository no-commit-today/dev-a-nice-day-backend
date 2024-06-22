package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.client.feed.FeedClient;
import com.nocommittoday.client.feed.FeedResponse;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;
import com.nocommittoday.techswipe.subscription.domain.FeedSubscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FeedContentReader {

    private final FeedClient feedClient;
    private final DocumentConnector documentConnector;
    private final DocumentElementExtractor documentElementExtractor;
    private final ContentCrawlerCreator contentCrawlerCreator;
    private final LocalDateParser localDateParser;

    public List<SubscribedContent> getList(
            final FeedSubscription subscription,
            final LocalDate date
    ) {
        final FeedResponse feed = feedClient.get(subscription.url()).get();
        final List<SubscribedContent> result = new ArrayList<>();

        for (FeedResponse.Entry entry : feed.entries()) {
            final ContentCrawler crawler = contentCrawlerCreator.create(
                    documentElementExtractor,
                    documentConnector, entry.link()
            );
            final LocalDate publishedDate = Optional.of(subscription.contentCrawling().date())
                    .filter(contentCrawling -> CrawlingType.NONE != contentCrawling.type())
                    .map(crawler::getText)
                    .map(localDateParser::parse)
                    .orElse(entry.date());
            if (date.isAfter(publishedDate)) {
                break;
            }
            final String imageUrl = crawler.getImageUrl();
            final String title = Optional.of(subscription.contentCrawling().title())
                    .filter(contentCrawling -> CrawlingType.NONE != contentCrawling.type())
                    .map(crawler::getText)
                    .orElse(entry.title());
            final String content = Optional.of(subscription.contentCrawling().content())
                    .filter(contentCrawling -> CrawlingType.NONE != contentCrawling.type())
                    .map(crawler::get)
                    .orElse(entry.content());

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

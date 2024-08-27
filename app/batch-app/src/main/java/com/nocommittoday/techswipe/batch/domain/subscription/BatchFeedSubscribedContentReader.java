package com.nocommittoday.techswipe.batch.domain.subscription;

import com.nocommittoday.techswipe.domain.subscription.FeedSubscription;
import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.nocommittoday.techswipe.domain.subscription.Subscription;
import com.nocommittoday.techswipe.domain.subscription.SubscriptionType;
import com.nocommittoday.techswipe.infrastructure.feed.Feed;
import com.nocommittoday.techswipe.infrastructure.feed.FeedReader;
import com.nocommittoday.techswipe.infrastructure.jsoup.HtmlTagCleaner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchFeedSubscribedContentReader implements BatchSubscribedContentReader {

    private final FeedReader feedReader;
    private final HtmlTagCleaner htmlTagCleaner;

    public BatchFeedSubscribedContentReader(FeedReader feedReader, HtmlTagCleaner htmlTagCleaner) {
        this.feedReader = feedReader;
        this.htmlTagCleaner = htmlTagCleaner;
    }

    @Override
    public List<SubscribedContent> getList(Subscription subscription, int page) {
        if (page != 1) {
            return List.of();
        }
        FeedSubscription feedSubscription = (FeedSubscription) subscription;
        String url = feedSubscription.getUrl();
        Feed feed = feedReader.read(url);
        return feed.entries().stream()
                .map(entry -> new SubscribedContent(
                        feedSubscription.getId(),
                        entry.link(),
                        !feedSubscription.isInitRequired(),
                        entry.title(),
                        null,
                        entry.date(),
                        entry.content() == null ? null : htmlTagCleaner.clean(entry.content())
                ))
                .toList();
    }

    @Override
    public boolean supports(Subscription subscription) {
        return SubscriptionType.FEED == subscription.getType()
                && subscription instanceof FeedSubscription;
    }
}

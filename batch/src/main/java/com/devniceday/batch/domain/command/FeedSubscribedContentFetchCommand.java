package com.devniceday.batch.domain.command;

import com.devniceday.batch.domain.FeedSubscription;
import com.devniceday.batch.domain.SubscribedContent;
import com.devniceday.batch.domain.SubscribedContentFetchCommand;
import com.devniceday.batch.domain.Subscription;
import com.devniceday.batch.domain.SubscriptionType;
import com.devniceday.batch.feed.Feed;
import com.devniceday.batch.feed.FeedClient;
import com.devniceday.batch.jsoup.HtmlTagCleaner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class FeedSubscribedContentFetchCommand implements SubscribedContentFetchCommand {

    private final FeedClient feedClient;
    private final HtmlTagCleaner htmlTagCleaner;

    public FeedSubscribedContentFetchCommand(FeedClient feedClient, HtmlTagCleaner htmlTagCleaner) {
        this.feedClient = feedClient;
        this.htmlTagCleaner = htmlTagCleaner;
    }

    @Override
    public List<SubscribedContent> fetch(Subscription subscription, int page) {
        if (page != 1) {
            return List.of();
        }
        FeedSubscription feedSubscription = Objects.requireNonNull(subscription.feed());
        String url = feedSubscription.url();
        Feed feed = feedClient.get(url);
        return feed.entries().stream()
                .map(entry -> new SubscribedContent(
                        subscription.id(),
                        entry.link(),
                        entry.title(),
                        null,
                        entry.date(),
                        entry.content() == null ? null : htmlTagCleaner.clean(entry.content())
                ))
                .toList();
    }

    @Override
    public boolean supports(Subscription subscription) {
        return SubscriptionType.FEED == subscription.type();
    }
}

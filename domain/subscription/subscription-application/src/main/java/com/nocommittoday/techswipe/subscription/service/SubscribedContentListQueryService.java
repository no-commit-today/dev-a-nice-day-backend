package com.nocommittoday.techswipe.subscription.service;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionType;
import com.nocommittoday.techswipe.subscription.infrastructure.FeedContentReader;
import com.nocommittoday.techswipe.subscription.infrastructure.ListCrawlingContentReader;
import com.nocommittoday.techswipe.subscription.infrastructure.SubscribedContent;
import com.nocommittoday.techswipe.subscription.infrastructure.SubscriptionReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribedContentListQueryService {

    private final SubscriptionReader subscriptionReader;
    private final FeedContentReader rssContentReader;
    private final ListCrawlingContentReader listCrawlingContentReader;

    public List<SubscribedContentResult> getList(
            final Subscription subscription, final LocalDate date
    ) {
        if (SubscriptionType.LIST_CRAWLING == subscription.getType()) {
            return getListCrawlingContents(subscription, date);
        } else if (SubscriptionType.FEED == subscription.getType()) {
            return getFeedContents(subscription, date);
        }
        throw new IllegalArgumentException("지원하지 않는 타입: " + subscription.getType());
    }

    public List<SubscribedContentResult> getInitList(final TechContentProvider.Id providerId) {
        final Subscription subscription = subscriptionReader.getByProviderId(providerId);
        final LocalDate date = LocalDate.MIN;
        if (SubscriptionType.LIST_CRAWLING == subscription.getInitType()) {
            return getListCrawlingContents(subscription, date);
        } else if (SubscriptionType.FEED == subscription.getInitType()) {
            return getFeedContents(subscription, date);
        }
        throw new IllegalArgumentException("지원하지 않는 타입: " + subscription.getInitType());
    }

    private List<SubscribedContentResult> getFeedContents(final Subscription subscription, final LocalDate date) {
        return rssContentReader.getList(subscription.toFeed(), date).stream()
                .map(subscribedContent -> convertToResult(subscribedContent, subscription.getType()))
                .toList();
    }

    private List<SubscribedContentResult> getListCrawlingContents(final Subscription subscription, final LocalDate date) {
        return subscription.toListCrawling().stream()
                .map(listCrawling -> listCrawlingContentReader.getList(listCrawling, date))
                .flatMap(List::stream)
                .map(subscribedContent -> convertToResult(subscribedContent, subscription.getType()))
                .toList();
    }

    private SubscribedContentResult convertToResult(final SubscribedContent content, SubscriptionType type) {
        return new SubscribedContentResult(
                type,
                content.url(),
                content.title(),
                content.imageUrl(),
                content.publishedDate(),
                content.content()
        );
    }
}

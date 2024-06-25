package com.nocommittoday.techswipe.subscription.service;

import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionType;
import com.nocommittoday.techswipe.subscription.infrastructure.SubscribedContent;
import com.nocommittoday.techswipe.subscription.infrastructure.SubscribedContentReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribedContentListQueryService {

    private final List<SubscribedContentReader> subscribedContentReaders;

    public List<SubscribedContentResult> getList(
            final Subscription subscription, final LocalDate date
    ) {
        for (SubscribedContentReader reader : subscribedContentReaders) {
            if (!reader.supports(subscription)) {
                continue;
            }
            return reader.getList(subscription, date).stream()
                    .map(subscribedContent -> mapToResult(subscribedContent, subscription.getType()))
                    .toList();
        }
        throw new IllegalArgumentException("지원하지 않는 타입: " + subscription.getType());
    }

    public List<SubscribedContentResult> getInitList(final Subscription subscription) {
        final LocalDate date = LocalDate.MIN;
        for (SubscribedContentReader reader : subscribedContentReaders) {
            if (!reader.supportsInit(subscription)) {
                continue;
            }
            return reader.getList(subscription, date).stream()
                    .map(subscribedContent -> mapToResult(subscribedContent, subscription.getInitType()))
                    .toList();
        }
        throw new IllegalArgumentException("지원하지 않는 타입: " + subscription.getInitType());
    }

    private SubscribedContentResult mapToResult(final SubscribedContent content, SubscriptionType type) {
        return new SubscribedContentResult(
                content.url(),
                content.title(),
                content.imageUrl(),
                content.publishedDate(),
                content.content()
        );
    }
}

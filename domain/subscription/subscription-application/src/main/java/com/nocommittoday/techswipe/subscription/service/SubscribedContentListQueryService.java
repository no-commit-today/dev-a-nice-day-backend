package com.nocommittoday.techswipe.subscription.service;

import com.nocommittoday.techswipe.subscription.domain.SubscribedContentResult;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.infrastructure.SubscribedContentReader;
import com.nocommittoday.techswipe.subscription.service.exception.NotSupportedSubscriptionInitTypeException;
import com.nocommittoday.techswipe.subscription.service.exception.NotSupportedSubscriptionTypeException;
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
            return reader.getList(subscription, date);
        }
        throw new NotSupportedSubscriptionTypeException(subscription.getId());
    }

    public List<SubscribedContentResult> getInitList(final Subscription subscription) {
        final LocalDate date = LocalDate.MIN;
        for (SubscribedContentReader reader : subscribedContentReaders) {
            if (!reader.supportsInit(subscription)) {
                continue;
            }
            return reader.getList(subscription, date);
        }
        throw new NotSupportedSubscriptionInitTypeException(subscription.getId());
    }

}

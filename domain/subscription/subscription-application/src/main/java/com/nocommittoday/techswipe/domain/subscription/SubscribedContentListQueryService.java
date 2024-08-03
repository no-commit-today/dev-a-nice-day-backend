package com.nocommittoday.techswipe.domain.subscription;

import com.nocommittoday.techswipe.domain.subscription.exception.SubscriptionSubscribeFailureException;
import com.nocommittoday.techswipe.domain.subscription.exception.CollectionInfrastructureWebException;
import com.nocommittoday.techswipe.domain.subscription.exception.NotSupportedSubscriptionInitTypeException;
import com.nocommittoday.techswipe.domain.subscription.exception.NotSupportedSubscriptionTypeException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SubscribedContentListQueryService {

    private final List<SubscribedContentReader> subscribedContentReaders;

    public SubscribedContentListQueryService(List<SubscribedContentReader> subscribedContentReaders) {
        this.subscribedContentReaders = subscribedContentReaders;
    }

    public List<SubscribedContent> getList(Subscription subscription, LocalDate date) {
        try {
            for (SubscribedContentReader reader : subscribedContentReaders) {
                if (!reader.supports(subscription)) {
                    continue;
                }
                return reader.getList(subscription, date);
            }
        } catch (CollectionInfrastructureWebException e) {
            throw new SubscriptionSubscribeFailureException(subscription.getId(), e);
        }
        throw new NotSupportedSubscriptionTypeException(subscription.getId());
    }

    public List<SubscribedContent> getInitList(Subscription subscription) {
        LocalDate date = LocalDate.MIN;
        try {
            for (SubscribedContentReader reader : subscribedContentReaders) {
                if (!reader.supportsInit(subscription)) {
                    continue;
                }
                return reader.getList(subscription, date);
            }
        } catch (CollectionInfrastructureWebException e) {
            throw new SubscriptionSubscribeFailureException(subscription.getId(), e);
        }
        throw new NotSupportedSubscriptionInitTypeException(subscription.getId());
    }

}

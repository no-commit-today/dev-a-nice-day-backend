package com.nocommittoday.techswipe.batch.domain.subscription;

import com.nocommittoday.techswipe.batch.domain.subscription.exception.NotSupportedSubscriptionException;
import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.nocommittoday.techswipe.domain.subscription.Subscription;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchSubscribedContentReaderDelegator {

    private final List<BatchSubscribedContentReader> readers;

    public BatchSubscribedContentReaderDelegator(List<BatchSubscribedContentReader> readers) {
        this.readers = readers;
    }

    public List<SubscribedContent> getList(Subscription subscription, int page) {
        for (BatchSubscribedContentReader reader : readers) {
            if (reader.supports(subscription)) {
                return reader.getList(subscription, page);
            }
        }

        throw new NotSupportedSubscriptionException(subscription.getId());
    }
}

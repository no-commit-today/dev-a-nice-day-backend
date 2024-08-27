package com.nocommittoday.techswipe.batch.domain.subscription;

import com.nocommittoday.techswipe.batch.domain.subscription.exception.NotSupportedSubscriptionException;
import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.nocommittoday.techswipe.domain.subscription.Subscription;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchSubscribedContentInitializerDelegator {

    private final List<BatchSubscribedContentInitializer> initializers;

    public BatchSubscribedContentInitializerDelegator(List<BatchSubscribedContentInitializer> initializers) {
        this.initializers = initializers;
    }

    public SubscribedContent initialize(Subscription subscription, SubscribedContent content) {
        if (subscription.getId() != content.getSubscriptionId()) {
            throw new IllegalArgumentException(
                    "SubscriptionId 가 일치하지 않습니다. Subscription.id="
                            + subscription.getId() + ", content.subscriptionId=" + content.getSubscriptionId()
            );
        }

        for (BatchSubscribedContentInitializer initializer : initializers) {
            if (initializer.supports(subscription)) {
                return initializer.initialize(subscription, content);
            }
        }

        throw new NotSupportedSubscriptionException(subscription.getId());
    }
}

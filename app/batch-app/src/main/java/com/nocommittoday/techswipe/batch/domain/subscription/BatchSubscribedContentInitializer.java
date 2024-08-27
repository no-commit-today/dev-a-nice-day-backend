package com.nocommittoday.techswipe.batch.domain.subscription;

import com.nocommittoday.techswipe.domain.subscription.SubscribedContent;
import com.nocommittoday.techswipe.domain.subscription.Subscription;

public interface BatchSubscribedContentInitializer {

    boolean supports(Subscription subscription);

    SubscribedContent initialize(Subscription subscription, SubscribedContent content);
}

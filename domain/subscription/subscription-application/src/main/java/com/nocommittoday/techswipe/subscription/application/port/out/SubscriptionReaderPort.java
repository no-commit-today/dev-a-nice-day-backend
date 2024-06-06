package com.nocommittoday.techswipe.subscription.application.port.out;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.subscription.domain.Subscription;

public interface SubscriptionReaderPort {

    Subscription getByProviderId(final TechContentProvider.TechContentProviderId providerId);
}

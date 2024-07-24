package com.nocommittoday.techswipe.subscription.domain.exception;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class SubscriptionNotFoundException extends AbstractDomainException {

    public SubscriptionNotFoundException(final TechContentProviderId providerId) {
        super(SubscriptionErrorCode.NOT_FOUND, "providerId=" + providerId.value());
    }
}

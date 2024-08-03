package com.nocommittoday.techswipe.domain.subscription.exception;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.domain.core.AbstractDomainException;

public class SubscriptionNotFoundException extends AbstractDomainException {

    public SubscriptionNotFoundException(TechContentProviderId providerId) {
        super(SubscriptionErrorCode.NOT_FOUND, "providerId=" + providerId.value());
    }
}

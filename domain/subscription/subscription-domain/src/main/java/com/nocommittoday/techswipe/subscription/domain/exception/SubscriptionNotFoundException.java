package com.nocommittoday.techswipe.subscription.domain.exception;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class SubscriptionNotFoundException extends AbstractDomainException {

    public SubscriptionNotFoundException(TechContentProviderId providerId) {
        super(SubscriptionErrorCode.NOT_FOUND, "providerId=" + providerId.value());
    }
}

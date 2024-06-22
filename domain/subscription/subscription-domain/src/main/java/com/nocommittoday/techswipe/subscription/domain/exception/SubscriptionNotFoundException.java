package com.nocommittoday.techswipe.subscription.domain.exception;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.core.domain.exception.AbstractDomainException;

public class SubscriptionNotFoundException extends AbstractDomainException {

    public SubscriptionNotFoundException(final TechContentProvider.Id providerId) {
        super(SubscriptionErrorCode.NOT_FOUND, "providerId=" + providerId.value());
    }
}

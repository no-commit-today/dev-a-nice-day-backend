package com.nocommittoday.techswipe.subscription.domain.exception;

import com.nocommittoday.techswipe.core.domain.AbstractDomainException;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionId;

public class SubscriptionSubscribeFailureException extends AbstractDomainException {

    public SubscriptionSubscribeFailureException(final SubscriptionId id, final Exception ex) {
        super(SubscriptionErrorCode.SUBSCRIBE_FAILURE, "subscription.id=" + id, ex);
    }
}

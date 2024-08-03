package com.nocommittoday.techswipe.domain.subscription.exception;

import com.nocommittoday.techswipe.domain.core.AbstractDomainException;
import com.nocommittoday.techswipe.domain.subscription.SubscriptionId;

public class SubscriptionSubscribeFailureException extends AbstractDomainException {

    public SubscriptionSubscribeFailureException(SubscriptionId id, Exception ex) {
        super(SubscriptionErrorCode.SUBSCRIBE_FAILURE, "subscription.id=" + id, ex);
    }
}

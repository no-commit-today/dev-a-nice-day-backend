package com.nocommittoday.techswipe.subscription.domain;

import com.nocommittoday.techswipe.core.domain.exception.AbstractDomainException;

public class SubscriptionRegisterFailureException extends AbstractDomainException {

    public SubscriptionRegisterFailureException(final String message) {
        super(SubscriptionErrorCode.REGISTER_FAILURE, message);
    }
}

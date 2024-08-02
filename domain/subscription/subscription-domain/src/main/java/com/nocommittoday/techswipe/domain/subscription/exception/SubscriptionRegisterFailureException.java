package com.nocommittoday.techswipe.domain.subscription.exception;

import com.nocommittoday.techswipe.core.domain.AbstractDomainException;

public class SubscriptionRegisterFailureException extends AbstractDomainException {

    public SubscriptionRegisterFailureException(String message) {
        super(SubscriptionErrorCode.REGISTER_FAILURE, message);
    }
}

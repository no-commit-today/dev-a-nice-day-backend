package com.nocommittoday.techswipe.domain.subscription.exception;

import com.nocommittoday.techswipe.domain.subscription.SubscriptionId;

public class NotSupportedSubscriptionTypeException extends SubscriptionApplicationException {

    public NotSupportedSubscriptionTypeException(SubscriptionId id) {
        super("지원하지 않는 타입. id= " + id);
    }
}

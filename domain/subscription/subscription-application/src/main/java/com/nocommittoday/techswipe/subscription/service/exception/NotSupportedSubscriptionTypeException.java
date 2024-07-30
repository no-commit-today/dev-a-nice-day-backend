package com.nocommittoday.techswipe.subscription.service.exception;

import com.nocommittoday.techswipe.subscription.domain.SubscriptionId;

public class NotSupportedSubscriptionTypeException extends SubscriptionApplicationException {

    public NotSupportedSubscriptionTypeException(SubscriptionId id) {
        super("지원하지 않는 타입. id= " + id);
    }
}

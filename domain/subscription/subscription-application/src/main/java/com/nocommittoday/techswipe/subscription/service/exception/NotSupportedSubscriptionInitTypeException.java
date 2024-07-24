package com.nocommittoday.techswipe.subscription.service.exception;

import com.nocommittoday.techswipe.subscription.domain.Subscription;

public class NotSupportedSubscriptionInitTypeException extends SubscriptionApplicationException {

    public NotSupportedSubscriptionInitTypeException(final Subscription.Id id) {
        super("지원하지 않는 초기화 타입. id=" + id);
    }
}

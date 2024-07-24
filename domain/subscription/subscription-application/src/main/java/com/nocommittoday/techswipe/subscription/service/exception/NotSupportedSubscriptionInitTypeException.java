package com.nocommittoday.techswipe.subscription.service.exception;

import com.nocommittoday.techswipe.subscription.domain.SubscriptionId;

public class NotSupportedSubscriptionInitTypeException extends SubscriptionApplicationException {

    public NotSupportedSubscriptionInitTypeException(final SubscriptionId id) {
        super("지원하지 않는 초기화 타입. id=" + id);
    }
}

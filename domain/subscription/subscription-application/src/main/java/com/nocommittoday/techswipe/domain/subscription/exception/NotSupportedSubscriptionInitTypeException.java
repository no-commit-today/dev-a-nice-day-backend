package com.nocommittoday.techswipe.domain.subscription.exception;

import com.nocommittoday.techswipe.domain.subscription.SubscriptionId;

public class NotSupportedSubscriptionInitTypeException extends SubscriptionApplicationException {

    public NotSupportedSubscriptionInitTypeException(SubscriptionId id) {
        super("지원하지 않는 초기화 타입. id=" + id);
    }
}

package com.nocommittoday.techswipe.batch.domain.subscription.exception;

import com.nocommittoday.techswipe.batch.domain.core.BatchDomainException;
import com.nocommittoday.techswipe.domain.subscription.SubscriptionId;

public class NotSupportedSubscriptionException extends BatchDomainException {

    public NotSupportedSubscriptionException(SubscriptionId id) {
        super("지원하지 않는 구독입니다. id=" + id);
    }
}

package com.nocommittoday.techswipe.subscription.application.port.out;

import com.nocommittoday.techswipe.subscription.domain.vo.SubscriptionRegister;

public interface SubscriptionSavePort {

    long save(final SubscriptionRegister register);
}

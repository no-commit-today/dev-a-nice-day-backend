package com.nocommittoday.techswipe.subscription.application.port.in;

import com.nocommittoday.techswipe.subscription.domain.vo.SubscriptionRegister;

public interface SubscriptionRegisterUseCase {

    long register(final SubscriptionRegister register);
}

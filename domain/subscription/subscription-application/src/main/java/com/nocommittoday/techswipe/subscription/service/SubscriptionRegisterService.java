package com.nocommittoday.techswipe.subscription.service;

import com.nocommittoday.techswipe.content.infrastructure.TechContentProviderIdValidator;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionRegister;
import com.nocommittoday.techswipe.subscription.infrastructure.SubscriptionUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionRegisterService {

    private final SubscriptionUpdater subscriptionUpdater;
    private final TechContentProviderIdValidator techContentProviderIdValidator;

    public Subscription.Id register(final SubscriptionRegister register) {
        register.validate();
        techContentProviderIdValidator.validate(register.providerId());
        return subscriptionUpdater.update(register);
    }
}

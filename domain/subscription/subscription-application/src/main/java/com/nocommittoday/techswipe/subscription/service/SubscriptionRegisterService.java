package com.nocommittoday.techswipe.subscription.service;

import com.nocommittoday.techswipe.domain.content.TechContentProviderIdValidator;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionId;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionRegister;
import com.nocommittoday.techswipe.subscription.infrastructure.SubscriptionUpdater;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionRegisterService {

    private final SubscriptionUpdater subscriptionUpdater;
    private final TechContentProviderIdValidator techContentProviderIdValidator;

    public SubscriptionRegisterService(
            SubscriptionUpdater subscriptionUpdater, TechContentProviderIdValidator techContentProviderIdValidator
    ) {
        this.subscriptionUpdater = subscriptionUpdater;
        this.techContentProviderIdValidator = techContentProviderIdValidator;
    }

    public SubscriptionId register(SubscriptionRegister register) {
        register.validate();
        techContentProviderIdValidator.validate(register.providerId());
        return subscriptionUpdater.update(register);
    }
}

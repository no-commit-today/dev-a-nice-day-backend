package com.nocommittoday.techswipe.subscription.service;

import com.nocommittoday.techswipe.content.infrastructure.ContentProviderIdValidator;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.vo.SubscriptionRegister;
import com.nocommittoday.techswipe.subscription.infrastructure.SubscriptionUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionRegisterService {

    private final SubscriptionUpdater subscriptionUpdater;
    private final ContentProviderIdValidator contentProviderIdValidator;

    public Subscription.SubscriptionId register(final SubscriptionRegister register) {
        register.validate();
        contentProviderIdValidator.validate(register.providerId());
        return subscriptionUpdater.update(register);
    }
}

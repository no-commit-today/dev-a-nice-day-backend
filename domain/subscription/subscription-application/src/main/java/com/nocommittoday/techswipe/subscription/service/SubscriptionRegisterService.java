package com.nocommittoday.techswipe.subscription.service;

import com.nocommittoday.techswipe.content.infrastructure.ContentProviderIdValidator;
import com.nocommittoday.techswipe.subscription.domain.vo.SubscriptionRegister;
import com.nocommittoday.techswipe.subscription.infrastructure.SubscriptionAppender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionRegisterService {

    private final SubscriptionAppender subscriptionSavePort;
    private final ContentProviderIdValidator contentProviderIdValidator;

    public long register(final SubscriptionRegister register) {
        register.validate();
        contentProviderIdValidator.validate(register.providerId());
        return subscriptionSavePort.save(register);
    }
}

package com.nocommittoday.techswipe.subscription.service;

import com.nocommittoday.techswipe.content.service.ProviderExistsValidationService;
import com.nocommittoday.techswipe.subscription.domain.vo.SubscriptionRegister;
import com.nocommittoday.techswipe.subscription.infrastructure.SubscriptionAppender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionRegisterService {

    private final SubscriptionAppender subscriptionSavePort;
    private final ProviderExistsValidationService providerExistsValidationService;

    public long register(final SubscriptionRegister register) {
        providerExistsValidationService.validate(register.providerId());
        register.validate();
        return subscriptionSavePort.save(register);
    }
}

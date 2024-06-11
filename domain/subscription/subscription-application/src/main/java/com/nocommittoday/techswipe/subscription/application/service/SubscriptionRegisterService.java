package com.nocommittoday.techswipe.subscription.application.service;

import com.nocommittoday.techswipe.content.application.port.in.ProviderExistsValidationUseCase;
import com.nocommittoday.techswipe.subscription.application.port.in.SubscriptionRegisterUseCase;
import com.nocommittoday.techswipe.subscription.domain.vo.SubscriptionRegister;
import com.nocommittoday.techswipe.subscription.infrastructure.SubscriptionAppender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class SubscriptionRegisterService implements SubscriptionRegisterUseCase {

    private final SubscriptionAppender subscriptionSavePort;
    private final ProviderExistsValidationUseCase providerExistsValidationUseCase;

    @Override
    public long register(final SubscriptionRegister register) {
        providerExistsValidationUseCase.validate(register.providerId());
        return subscriptionSavePort.save(register);
    }
}

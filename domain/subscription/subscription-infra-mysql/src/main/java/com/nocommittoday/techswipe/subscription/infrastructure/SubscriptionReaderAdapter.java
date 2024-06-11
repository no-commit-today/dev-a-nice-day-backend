package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.subscription.application.port.out.SubscriptionReaderPort;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.exception.SubscriptionNotFoundException;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionEntity;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class SubscriptionReaderAdapter implements SubscriptionReaderPort {

    private final SubscriptionJpaRepository subscriptionRepository;

    @Override
    public Subscription getByProviderId(final TechContentProvider.TechContentProviderId providerId) {
        final SubscriptionEntity subscriptionEntity = subscriptionRepository.findByProvider(
                        TechContentProviderEntity.from(providerId)
                ).orElseThrow(() -> new SubscriptionNotFoundException(providerId));
        return subscriptionEntity.toDomain();
    }
}

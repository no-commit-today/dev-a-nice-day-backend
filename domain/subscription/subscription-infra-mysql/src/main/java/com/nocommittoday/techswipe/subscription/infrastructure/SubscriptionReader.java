package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProviderId;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.exception.SubscriptionNotFoundException;
import com.nocommittoday.techswipe.storage.mysql.subscription.SubscriptionEntity;
import com.nocommittoday.techswipe.storage.mysql.subscription.SubscriptionJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SubscriptionReader {

    private final SubscriptionJpaRepository subscriptionRepository;

    public SubscriptionReader(SubscriptionJpaRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Subscription getByProviderId(TechContentProviderId providerId) {
        SubscriptionEntity subscriptionEntity = subscriptionRepository.findByProvider(
                        TechContentProviderEntity.from(providerId)
                )
                .filter(SubscriptionEntity::isUsed)
                .orElseThrow(() -> new SubscriptionNotFoundException(providerId));
        return subscriptionEntity.toDomain();
    }
}

package com.nocommittoday.techswipe.domain.subscription;

import com.nocommittoday.techswipe.domain.content.TechContentProviderId;
import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import com.nocommittoday.techswipe.domain.subscription.exception.SubscriptionNotFoundException;
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

package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.content.domain.TechContentProvider;
import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.exception.SubscriptionNotFoundException;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionEntity;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SubscriptionReader {

    private final SubscriptionJpaRepository subscriptionRepository;

    public Subscription getByProviderId(final TechContentProvider.Id providerId) {
        final SubscriptionEntity subscriptionEntity = subscriptionRepository.findByProvider(
                        TechContentProviderEntity.from(providerId)
                )
                .filter(SubscriptionEntity::isUsed)
                .orElseThrow(() -> new SubscriptionNotFoundException(providerId));
        return subscriptionEntity.toDomain();
    }
}

package com.nocommittoday.techswipe.domain.subscription;

import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import com.nocommittoday.techswipe.storage.mysql.subscription.SubscriptionEntity;
import com.nocommittoday.techswipe.storage.mysql.subscription.SubscriptionJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SubscriptionUpdater {

    private final SubscriptionJpaRepository subscriptionJpaRepository;

    public SubscriptionUpdater(SubscriptionJpaRepository subscriptionJpaRepository) {
        this.subscriptionJpaRepository = subscriptionJpaRepository;
    }

    public SubscriptionId update(SubscriptionRegister register) {
        return new SubscriptionId(
                subscriptionJpaRepository.findByProvider(
                                TechContentProviderEntity.from(register.providerId())
                        ).map(entity -> {
                            entity.restore();
                            entity.update(register);
                            return subscriptionJpaRepository.save(entity);
                        }).orElseGet(() -> subscriptionJpaRepository.save(SubscriptionEntity.from(register)))
                        .getId()
        );
    }
}

package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionId;
import com.nocommittoday.techswipe.subscription.domain.SubscriptionRegister;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionEntity;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionJpaRepository;
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

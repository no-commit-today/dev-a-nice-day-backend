package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.domain.vo.SubscriptionRegister;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionEntity;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SubscriptionUpdater {

    private final SubscriptionJpaRepository subscriptionJpaRepository;

    public Subscription.SubscriptionId update(final SubscriptionRegister register) {
        return new Subscription.SubscriptionId(
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

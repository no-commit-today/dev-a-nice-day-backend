package com.nocommittoday.techswipe.subscription.adapter.out.mysql;

import com.nocommittoday.techswipe.subscription.application.port.out.SubscriptionSavePort;
import com.nocommittoday.techswipe.subscription.domain.vo.SubscriptionRegister;
import com.nocommittoday.techswipe.subscription.infrastructure.mysql.SubscriptionEntity;
import com.nocommittoday.techswipe.subscription.infrastructure.mysql.SubscriptionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class SubscriptionSaveAdapter implements SubscriptionSavePort {

    private final SubscriptionJpaRepository subscriptionJpaRepository;

    @Override
    public long save(final SubscriptionRegister register) {
        return subscriptionJpaRepository.save(SubscriptionEntity.from(register)).getId();
    }
}

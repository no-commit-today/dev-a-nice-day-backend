package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.subscription.domain.vo.SubscriptionRegister;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionEntity;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SubscriptionAppender {

    private final SubscriptionJpaRepository subscriptionJpaRepository;

    public long save(final SubscriptionRegister register) {
        return subscriptionJpaRepository.save(SubscriptionEntity.from(register)).getId();
    }
}

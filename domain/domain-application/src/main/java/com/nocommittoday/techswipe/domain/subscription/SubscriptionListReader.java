package com.nocommittoday.techswipe.domain.subscription;

import com.nocommittoday.techswipe.storage.mysql.subscription.SubscriptionEntity;
import com.nocommittoday.techswipe.storage.mysql.subscription.SubscriptionJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubscriptionListReader {

    private final SubscriptionJpaRepository subscriptionJpaRepository;

    public SubscriptionListReader(SubscriptionJpaRepository subscriptionJpaRepository) {
        this.subscriptionJpaRepository = subscriptionJpaRepository;
    }

    public List<Subscription> getList(int page, int size) {
        return subscriptionJpaRepository.findAllByDeletedIsFalse(
                PageRequest.of(page - 1, size, Sort.by("id"))
        ).stream().map(SubscriptionEntity::toDomain).toList();
    }
}

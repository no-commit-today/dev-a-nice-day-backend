package com.nocommittoday.techswipe.subscription.infrastructure;

import com.nocommittoday.techswipe.subscription.domain.Subscription;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionEntity;
import com.nocommittoday.techswipe.subscription.storage.mysql.SubscriptionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class SubscriptionListReader {

    private final SubscriptionJpaRepository subscriptionJpaRepository;

    public List<Subscription> getList(int page, int size) {
        return subscriptionJpaRepository.findAllByDeletedIsFalse(
                PageRequest.of(page - 1, size, Sort.by("id"))
        ).stream().map(SubscriptionEntity::toDomain).toList();
    }
}

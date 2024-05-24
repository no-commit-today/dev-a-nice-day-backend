package com.nocommittoday.techswipe.subscription.infrastructure.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionJpaRepository extends JpaRepository<SubscriptionEntity, Long> {
}

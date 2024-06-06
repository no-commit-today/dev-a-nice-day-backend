package com.nocommittoday.techswipe.subscription.storage.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionJpaRepository extends JpaRepository<SubscriptionEntity, Long> {

}

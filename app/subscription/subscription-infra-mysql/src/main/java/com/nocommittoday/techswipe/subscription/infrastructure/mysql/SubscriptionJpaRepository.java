package com.nocommittoday.techswipe.subscription.infrastructure.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionJpaRepository extends JpaRepository<SubscriptionEntity, Long> {

    Optional<SubscriptionEntity> findByProviderId(final TechContentProviderIdEmbeddable providerId);
}

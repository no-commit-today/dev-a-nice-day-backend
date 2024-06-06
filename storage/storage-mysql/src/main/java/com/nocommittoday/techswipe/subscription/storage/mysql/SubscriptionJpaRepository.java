package com.nocommittoday.techswipe.subscription.storage.mysql;

import com.nocommittoday.techswipe.content.storage.mysql.TechContentProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionJpaRepository extends JpaRepository<SubscriptionEntity, Long> {

    Optional<SubscriptionEntity> findByProvider(TechContentProviderEntity provider);

}

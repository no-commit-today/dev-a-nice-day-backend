package com.nocommittoday.techswipe.storage.mysql.subscription;

import com.nocommittoday.techswipe.storage.mysql.content.TechContentProviderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionJpaRepository extends JpaRepository<SubscriptionEntity, Long> {

    Optional<SubscriptionEntity> findByProvider(TechContentProviderEntity provider);

    List<SubscriptionEntity> findAllByDeletedIsFalse(Pageable pageable);
}

package com.nocommittoday.techswipe.storage.mysql.admin;

import com.nocommittoday.techswipe.storage.mysql.collection.CollectedContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminCollectedContentEntityJpaRepository
        extends JpaRepository<CollectedContentEntity, Long>, AdminCollectedContentEntityJpaRepositoryCustom {

    boolean existsByUrl(String url);
}

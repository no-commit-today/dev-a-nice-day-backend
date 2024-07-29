package com.nocommittoday.techswipe.collection.storage.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectedContentJpaRepository
        extends JpaRepository<CollectedContentEntity, Long>, CollectedContentJpaRepositoryCustom {

    boolean existsByUrl(String url);
}

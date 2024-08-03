package com.nocommittoday.techswipe.storage.mysql.collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectedContentJpaRepository
        extends JpaRepository<CollectedContentEntity, Long>, CollectedContentJpaRepositoryCustom {

    boolean existsByUrl(String url);
}

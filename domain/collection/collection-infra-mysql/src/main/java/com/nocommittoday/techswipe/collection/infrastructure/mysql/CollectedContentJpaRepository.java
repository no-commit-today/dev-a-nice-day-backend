package com.nocommittoday.techswipe.collection.infrastructure.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectedContentJpaRepository extends JpaRepository<CollectedContentEntity, Long> {
}

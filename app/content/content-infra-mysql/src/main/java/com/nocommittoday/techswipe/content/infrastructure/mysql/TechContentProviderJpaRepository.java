package com.nocommittoday.techswipe.content.infrastructure.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TechContentProviderJpaRepository extends JpaRepository<TechContentProviderEntity, Long> {
}

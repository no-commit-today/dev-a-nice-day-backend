package com.nocommittoday.techswipe.storage.mysql.content;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TechContentJpaQueryRepository
        extends JpaRepository<TechContentEntity, Long>, TechContentJpaQueryRepositoryCustom {
}

package com.nocommittoday.techswipe.storage.mysql.content;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TechContentJpaRepository
        extends JpaRepository<TechContentEntity, Long>, TechContentJpaRepositoryCustom {
}

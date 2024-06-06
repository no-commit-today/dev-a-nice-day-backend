package com.nocommittoday.techswipe.content.storage.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TechContentJpaRepository
        extends JpaRepository<TechContentEntity, Long>, TechContentJpaRepositoryCustom
{
}

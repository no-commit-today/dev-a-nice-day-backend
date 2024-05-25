package com.nocommittoday.techswipe.content.infrastructure.mysql;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TechContentJpaRepository
        extends JpaRepository<TechContentEntity, Long>, TechContentJpaRepositoryCustom
{

    @EntityGraph(attributePaths = {"provider"})
    List<TechContentEntity> findAllWithProvider(final Pageable pageable);
}

package com.nocommittoday.techswipe.content.storage.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechContentJpaRepository
        extends JpaRepository<TechContentEntity, Long>, TechContentJpaRepositoryCustom {

    boolean existsByIdAndDeletedIsFalse(Long id);

    boolean existsByUrlAndDeletedIsFalse(String url);

    Optional<TechContentEntity> findByUrlAndDeletedIsFalse(String url);
}

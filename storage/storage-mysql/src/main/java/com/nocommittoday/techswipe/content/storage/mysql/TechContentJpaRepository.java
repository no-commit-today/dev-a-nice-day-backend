package com.nocommittoday.techswipe.content.storage.mysql;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TechContentJpaRepository
        extends JpaRepository<TechContentEntity, Long>, TechContentJpaRepositoryCustom {

    boolean existsByIdAndDeletedIsFalse(Long id);

    boolean existsByUrlAndDeletedIsFalse(String url);

    Optional<TechContentEntity> findByUrlAndDeletedIsFalse(String url);

    List<TechContentEntity> findAllByLastModifiedAtGreaterThanEqualAndLastModifiedAtLessThanAndDeletedIsFalse(
            Pageable pageable, LocalDateTime lastModifiedAtStart, LocalDateTime lastModifiedAtEnd);
}

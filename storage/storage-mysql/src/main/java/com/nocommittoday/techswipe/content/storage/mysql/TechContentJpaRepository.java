package com.nocommittoday.techswipe.content.storage.mysql;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TechContentJpaRepository
        extends JpaRepository<TechContentEntity, Long>, TechContentJpaRepositoryCustom {

    List<TechContentEntity> findAllByLastModifiedAtGreaterThanEqualAndLastModifiedAtLessThan(
            Pageable pageable, LocalDateTime lastModifiedAtStart, LocalDateTime lastModifiedAtEnd);
}

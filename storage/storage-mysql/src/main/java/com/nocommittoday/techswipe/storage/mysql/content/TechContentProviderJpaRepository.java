package com.nocommittoday.techswipe.storage.mysql.content;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TechContentProviderJpaRepository extends JpaRepository<TechContentProviderEntity, Long> {

    boolean existsByIdAndDeletedIsFalse(Long id);

    boolean existsByUrl(String url);

    List<TechContentProviderEntity> findAllByLastModifiedAtGreaterThanEqualAndLastModifiedAtLessThan(
            Pageable pageable, LocalDateTime lastModifiedAtStart, LocalDateTime lastModifiedAtEnd);
}

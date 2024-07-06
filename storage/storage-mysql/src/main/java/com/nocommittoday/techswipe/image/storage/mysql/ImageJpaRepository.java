package com.nocommittoday.techswipe.image.storage.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ImageJpaRepository extends JpaRepository<ImageEntity, Long> {

    boolean existsByIdAndDeletedIsFalse(Long id);

    List<ImageEntity> findAllByLastModifiedAtBetweenAndDeletedIsFalse(
            LocalDateTime lastModifiedAtStart, LocalDateTime lastModifiedAtEnd);
}

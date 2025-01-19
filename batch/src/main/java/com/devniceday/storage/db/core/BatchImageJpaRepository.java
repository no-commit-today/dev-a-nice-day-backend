package com.devniceday.storage.db.core;

import com.nocommittoday.techswipe.storage.mysql.image.ImageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BatchImageJpaRepository extends JpaRepository<ImageEntity, Long> {

    boolean existsByIdAndDeletedIsFalse(Long id);

    List<ImageEntity> findAllByLastModifiedAtGreaterThanEqualAndLastModifiedAtLessThan(
            Pageable pageable, LocalDateTime lastModifiedAtStart, LocalDateTime lastModifiedAtEnd);
}

package com.nocommittoday.techswipe.storage.mysql.image;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ImageJpaRepository extends JpaRepository<ImageEntity, Long> {

    boolean existsByIdAndDeletedIsFalse(Long id);

    List<ImageEntity> findAllByLastModifiedAtGreaterThanEqualAndLastModifiedAtLessThan(
            Pageable pageable, LocalDateTime lastModifiedAtStart, LocalDateTime lastModifiedAtEnd);
}

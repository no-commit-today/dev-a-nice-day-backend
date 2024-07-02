package com.nocommittoday.techswipe.image.storage.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJpaRepository extends JpaRepository<ImageEntity, Long> {

    boolean existsByIdAndDeletedIsFalse(Long id);
}

package com.nocommittoday.techswipe.image.infrastructure.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJpaRepository extends JpaRepository<ImageEntity, Long> {
}

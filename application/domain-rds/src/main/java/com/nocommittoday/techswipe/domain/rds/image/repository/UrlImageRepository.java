package com.nocommittoday.techswipe.domain.rds.image.repository;

import com.nocommittoday.techswipe.domain.rds.image.UrlImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlImageRepository extends JpaRepository<UrlImage, Long> {
}

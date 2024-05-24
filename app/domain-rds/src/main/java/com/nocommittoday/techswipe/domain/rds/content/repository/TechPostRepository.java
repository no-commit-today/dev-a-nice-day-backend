package com.nocommittoday.techswipe.domain.rds.content.repository;

import com.nocommittoday.techswipe.domain.rds.content.TechPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechPostRepository extends JpaRepository<TechPost, Long>, TechPostRepositoryCustom {
}

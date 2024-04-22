package com.nocommittoday.techswipe.domain.rds.provider.repository;

import com.nocommittoday.techswipe.domain.rds.provider.TechBlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechBlogRepository extends JpaRepository<TechBlog, Long> {
}

package com.nocommittoday.techswipe.domain.rds.provider.repository;

import com.nocommittoday.techswipe.domain.rds.provider.TechBlog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechBlogRepository extends JpaRepository<TechBlog, Long> {

    Optional<TechBlog> findByUrl(String url);
}

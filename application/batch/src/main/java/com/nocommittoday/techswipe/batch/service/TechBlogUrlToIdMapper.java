package com.nocommittoday.techswipe.batch.service;

import com.nocommittoday.techswipe.domain.rds.provider.TechBlog;
import com.nocommittoday.techswipe.domain.rds.provider.repository.TechBlogRepository;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@JobScope
@Component
@RequiredArgsConstructor
public class TechBlogUrlToIdMapper {

    private final TechBlogRepository techBlogRepository;

    private final Map<String, Long> urlToIdMap = Collections.synchronizedMap(new HashMap<>());

    @PostConstruct
    public void init() {
        techBlogRepository.findAll().forEach(techBlog -> urlToIdMap.put(techBlog.getUrl(), techBlog.getId()));
    }

    @Nullable
    public Long map(final String url) {
        return techBlogRepository.findByUrl(url)
                .map(TechBlog::getId)
                .orElse(null);
    }
}

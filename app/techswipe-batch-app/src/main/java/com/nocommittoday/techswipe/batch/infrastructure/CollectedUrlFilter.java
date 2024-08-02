package com.nocommittoday.techswipe.batch.infrastructure;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class CollectedUrlFilter {

    private static final Logger log = LoggerFactory.getLogger(CollectedUrlFilter.class);

    private final Set<String> urlSet;

    public CollectedUrlFilter(Set<String> urlSet) {
        this.urlSet = urlSet;
    }

    public boolean doFilter(String url) {
        boolean filtered = !urlSet.contains(url);
        if (!filtered) {
            log.info("url[{}] 이 존재합니다.", url);
        }
        return filtered;
    }
}

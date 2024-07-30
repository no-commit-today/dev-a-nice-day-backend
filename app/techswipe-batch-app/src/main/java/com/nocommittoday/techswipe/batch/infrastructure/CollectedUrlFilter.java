package com.nocommittoday.techswipe.batch.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class CollectedUrlFilter {

    private final Set<String> urlSet;

    public boolean doFilter(String url) {
        boolean filtered = !urlSet.contains(url);
        if (!filtered) {
            log.info("url[{}] 이 존재합니다.", url);
        }
        return filtered;
    }
}

package com.nocommittoday.techswipe.domain.subscription;

import javax.annotation.Nullable;
import java.util.List;

public record Crawling(
        CrawlingType type,
        @Nullable String selector,
        @Nullable List<Integer> indexes
) {
}

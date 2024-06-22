package com.nocommittoday.techswipe.subscription.domain;

import javax.annotation.Nullable;
import java.util.List;

public record Crawling(
        CrawlingType type,
        @Nullable String selector,
        @Nullable List<Integer> indexes
) {
}

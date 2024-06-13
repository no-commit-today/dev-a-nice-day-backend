package com.nocommittoday.techswipe.subscription.domain.vo;

import com.nocommittoday.techswipe.subscription.domain.enums.CrawlingType;

import javax.annotation.Nullable;
import java.util.List;

public record Crawling(
        CrawlingType type,
        @Nullable String selector,
        @Nullable List<Integer> indexes
) {
}

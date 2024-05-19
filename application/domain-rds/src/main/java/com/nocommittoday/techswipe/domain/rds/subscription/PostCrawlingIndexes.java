package com.nocommittoday.techswipe.domain.rds.subscription;

import jakarta.annotation.Nullable;

import java.util.List;


public record PostCrawlingIndexes(
        @Nullable List<Integer> title,
        @Nullable List<Integer> date,
        @Nullable List<Integer> content
) {
}

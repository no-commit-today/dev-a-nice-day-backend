package com.nocommittoday.techswipe.domain.rds.subscription;

import jakarta.annotation.Nullable;


public record PostCrawlingSelectors(
        @Nullable String title,
        @Nullable String date,
        @Nullable String content
) {
}

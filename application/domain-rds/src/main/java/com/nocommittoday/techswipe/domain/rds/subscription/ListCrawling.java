package com.nocommittoday.techswipe.domain.rds.subscription;

import jakarta.annotation.Nullable;
import lombok.Builder;

@Builder
public record ListCrawling(
        String url,
        String selector,
        @Nullable String pageUrlFormat
){
}

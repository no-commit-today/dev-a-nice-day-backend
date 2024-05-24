package com.nocommittoday.techswipe.domain.rds.subscription;

import jakarta.annotation.Nullable;
import lombok.Builder;

import java.util.List;

@Builder
public record ListCrawling(
        String url,
        List<Integer> indexes,
        @Nullable String pageUrlFormat
){
}

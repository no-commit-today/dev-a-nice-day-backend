package com.nocommittoday.techswipe.subscription.domain.vo;

import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;

@Builder
public record ListCrawling(
        @NonNull String url,
        @NonNull Crawling crawling,
        @Nullable String pageUrlFormat
){
}

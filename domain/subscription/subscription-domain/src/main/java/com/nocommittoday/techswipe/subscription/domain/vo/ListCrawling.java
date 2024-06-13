package com.nocommittoday.techswipe.subscription.domain.vo;

import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;

@Builder
public record ListCrawling(
        String url,
        Crawling crawling,
        @Nullable String pageUrlFormat
){
}

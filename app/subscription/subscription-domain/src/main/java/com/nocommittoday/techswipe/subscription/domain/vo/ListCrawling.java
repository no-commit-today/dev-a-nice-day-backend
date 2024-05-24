package com.nocommittoday.techswipe.subscription.domain.vo;

import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;

@Builder
public record ListCrawling(
        @NonNull String url,
        @NonNull List<Integer> indexes,
        @Nullable String pageUrlFormat
){
}

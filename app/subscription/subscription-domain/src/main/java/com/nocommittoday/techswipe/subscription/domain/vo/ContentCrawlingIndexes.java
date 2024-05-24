package com.nocommittoday.techswipe.subscription.domain.vo;


import javax.annotation.Nullable;
import java.util.List;


public record ContentCrawlingIndexes(
        @Nullable List<Integer> title,
        @Nullable List<Integer> date,
        @Nullable List<Integer> content
) {
}

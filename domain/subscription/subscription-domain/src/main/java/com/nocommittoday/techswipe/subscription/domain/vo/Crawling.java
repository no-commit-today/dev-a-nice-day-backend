package com.nocommittoday.techswipe.subscription.domain.vo;

import com.nocommittoday.techswipe.subscription.domain.enums.CrawlingType;

import javax.annotation.Nullable;
import java.util.List;

public record Crawling(
        CrawlingType type,
        @Nullable String selector,
        @Nullable List<Integer> indexes
) {

    public Crawling {
        if (CrawlingType.INDEX == type && (indexes == null || indexes.isEmpty())) {
            throw new IllegalArgumentException(CrawlingType.INDEX + "타입은 indexes가 필수입니다.");
        }
        if (CrawlingType.SELECTOR == type && (selector == null || selector.isEmpty())) {
            throw new IllegalArgumentException(CrawlingType.SELECTOR + "타입은 selector가 필수입니다.");
        }
    }

    public static Crawling ofNone() {
        return new Crawling(CrawlingType.NONE, null, null);
    }

    public static Crawling ofSelector(final String selector) {
        return new Crawling(CrawlingType.SELECTOR, selector, null);
    }

    public static Crawling ofIndex(final List<Integer> indexes) {
        return new Crawling(CrawlingType.INDEX, null, indexes);
    }
}

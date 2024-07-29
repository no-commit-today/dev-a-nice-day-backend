package com.nocommittoday.techswipe.subscription.storage.mysql;

import com.nocommittoday.techswipe.subscription.domain.Crawling;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CrawlingData {

    private CrawlingType type;

    @Nullable
    private String selector;

    @Nullable
    private List<Integer> indexes;

    public static CrawlingData from(final Crawling crawling) {
        return new CrawlingData(
                crawling.type(),
                crawling.selector(),
                crawling.indexes()
        );
    }

    public Crawling toDomain() {
        return new Crawling(
                type,
                selector,
                indexes
        );
    }
}

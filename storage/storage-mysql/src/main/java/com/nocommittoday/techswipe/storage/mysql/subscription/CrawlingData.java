package com.nocommittoday.techswipe.storage.mysql.subscription;

import com.nocommittoday.techswipe.subscription.domain.Crawling;
import com.nocommittoday.techswipe.subscription.domain.CrawlingType;

import javax.annotation.Nullable;
import java.util.List;

public class CrawlingData {

    private CrawlingType type;

    @Nullable
    private String selector;

    @Nullable
    private List<Integer> indexes;

    protected CrawlingData() {
    }

    public CrawlingData(CrawlingType type, String selector, List<Integer> indexes) {
        this.type = type;
        this.selector = selector;
        this.indexes = indexes;
    }

    public static CrawlingData from(Crawling crawling) {
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

    @Nullable
    public List<Integer> getIndexes() {
        return indexes;
    }

    @Nullable
    public String getSelector() {
        return selector;
    }

    public CrawlingType getType() {
        return type;
    }
}

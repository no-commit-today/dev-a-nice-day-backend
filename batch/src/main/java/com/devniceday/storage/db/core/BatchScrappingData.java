package com.devniceday.storage.db.core;


import com.devniceday.batch.domain.ScrappingType;

import javax.annotation.Nullable;
import java.util.List;

public class BatchScrappingData {

    private ScrappingType type;

    @Nullable
    private String selector;

    @Nullable
    private List<Integer> indexes;

    public BatchScrappingData(ScrappingType type, @Nullable List<Integer> indexes, @Nullable String selector) {
        this.type = type;
        this.indexes = indexes;
        this.selector = selector;
    }

    public ScrappingType getType() {
        return type;
    }

    @Nullable
    public List<Integer> getIndexes() {
        return indexes;
    }

    @Nullable
    public String getSelector() {
        return selector;
    }
}

package com.nocommittoday.techswipe.domain.subscription;

import java.util.List;

public class IndexScrapping extends Scrapping {

    private final List<Integer> indexes;

    IndexScrapping(List<Integer> indexes) {
        super(ScrappingType.INDEX);
        this.indexes = indexes;
    }

    public List<Integer> getIndexes() {
        return indexes;
    }
}

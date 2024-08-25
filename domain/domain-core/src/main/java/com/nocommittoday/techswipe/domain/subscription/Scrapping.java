package com.nocommittoday.techswipe.domain.subscription;

import java.util.List;


public class Scrapping {

    private final ScrappingType type;

    protected Scrapping(ScrappingType type) {
        this.type = type;
    }

    public static Scrapping createNoneScrapping() {
        return new Scrapping(ScrappingType.NONE);
    }

    public static IndexScrapping createIndexScrapping(List<Integer> indexes) {
        return new IndexScrapping(indexes);
    }

    public static SelectorScrapping createSelectorScrapping(String selector) {
        return new SelectorScrapping(selector);
    }

    public static Scrapping createTitleScrapping() {
        return new Scrapping(ScrappingType.TITLE);
    }

    public static Scrapping createOpenGraphImageScrapping() {
        return new Scrapping(ScrappingType.OPEN_GRAPH_IMAGE);
    }

    public ScrappingType getType() {
        return type;
    }
}

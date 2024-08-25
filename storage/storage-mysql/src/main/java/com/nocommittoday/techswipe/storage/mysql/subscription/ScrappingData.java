package com.nocommittoday.techswipe.storage.mysql.subscription;

import com.nocommittoday.techswipe.domain.subscription.Scrapping;
import com.nocommittoday.techswipe.domain.subscription.ScrappingType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class ScrappingData {

    private ScrappingType type;

    @Nullable
    private String selector;

    @Nullable
    private List<Integer> indexes;

    protected ScrappingData() {
    }

    public ScrappingData(ScrappingType type, @Nullable List<Integer> indexes, @Nullable String selector) {
        this.type = type;
        this.indexes = indexes;
        this.selector = selector;
    }

    public Scrapping toDomain() {
        return switch (type) {
            case NONE -> Scrapping.createNoneScrapping();
            case TITLE -> Scrapping.createTitleScrapping();
            case OPEN_GRAPH_IMAGE ->  Scrapping.createOpenGraphImageScrapping();
            case INDEX -> Scrapping.createIndexScrapping(Objects.requireNonNull(indexes));
            case SELECTOR -> Scrapping.createSelectorScrapping(Objects.requireNonNull(selector));
        };
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

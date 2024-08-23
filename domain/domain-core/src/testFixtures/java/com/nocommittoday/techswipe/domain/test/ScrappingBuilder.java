package com.nocommittoday.techswipe.domain.test;

import com.nocommittoday.techswipe.domain.subscription.Scrapping;

import java.util.List;

public class ScrappingBuilder {

    public static Scrapping none() {
        return Scrapping.createNoneScrapping();
    }

    public static Scrapping title() {
        return Scrapping.createTitleScrapping();
    }

    public static Scrapping openGraphImage() {
        return Scrapping.createOpenGraphImageScrapping();
    }

    public static Scrapping index(List<Integer> indexes) {
        return Scrapping.createIndexScrapping(indexes);
    }

    public static Scrapping index() {
        return Scrapping.createIndexScrapping(List.of(
                (int) LocalAutoIncrementIdUtils.nextId(),
                (int) LocalAutoIncrementIdUtils.nextId(),
                (int) LocalAutoIncrementIdUtils.nextId()
        ));
    }

    public static Scrapping selector(String selector) {
        return Scrapping.createSelectorScrapping(selector);
    }

    public static Scrapping selector() {
        return Scrapping.createSelectorScrapping("selector-" + LocalAutoIncrementIdUtils.nextId());
    }
}

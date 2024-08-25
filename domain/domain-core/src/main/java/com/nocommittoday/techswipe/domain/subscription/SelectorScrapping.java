package com.nocommittoday.techswipe.domain.subscription;

public class SelectorScrapping extends Scrapping {

    private final String selector;

    SelectorScrapping(String selector) {
        super(ScrappingType.SELECTOR);
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}

package com.nocommittoday.techswipe.domain.test;

import com.nocommittoday.techswipe.domain.subscription.ListScrapping;
import com.nocommittoday.techswipe.domain.subscription.Scrapping;

import javax.annotation.Nullable;

public class ListScrappingBuilder {

    @Nullable
    private String url;

    @Nullable
    private Scrapping scrapping;

    @Nullable
    private String pageUrlFormat;

    @Nullable
    private String contentUrlFormat;

    public ListScrappingBuilder() {
    }

    public static ListScrapping create() {
        return new ListScrappingBuilder().build();
    }

    public ListScrappingBuilder url(String url) {
        this.url = url;
        return this;
    }

    public ListScrappingBuilder scrapping(Scrapping scrapping) {
        this.scrapping = scrapping;
        return this;
    }

    public ListScrappingBuilder pageUrlFormat(String pageUrlFormat) {
        this.pageUrlFormat = pageUrlFormat;
        return this;
    }

    public ListScrappingBuilder contentUrlFormat(String contentUrlFormat) {
        this.contentUrlFormat = contentUrlFormat;
        return this;
    }

    public ListScrapping build() {
        fillRequiredFields();
        return new ListScrapping(url, scrapping, pageUrlFormat, contentUrlFormat);
    }

    private void fillRequiredFields() {
        if (url == null) {
            url = "url-" + LocalAutoIncrementIdUtils.nextId();
        }
        if (scrapping == null) {
            scrapping = ScrappingBuilder.selector();
        }
    }
}

package com.devniceday.batch.domain.test;

import com.devniceday.batch.domain.ListScrapping;
import com.devniceday.batch.domain.Scrapping;
import com.devniceday.test.java.LongIncrementUtil;

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
            url = "url-" + LongIncrementUtil.next();
        }
        if (scrapping == null) {
            scrapping = ScrappingBuilder.selector();
        }
    }
}

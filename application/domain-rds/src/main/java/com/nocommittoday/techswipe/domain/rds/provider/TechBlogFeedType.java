package com.nocommittoday.techswipe.domain.rds.provider;

import com.nocommittoday.module.domain.code.DomainCode;

public enum TechBlogFeedType implements DomainCode {

    RSS("RSS"),
    ATOM("ATOM"),
    SCARPING("스프래핑"),
    ;

    private final String title;

    TechBlogFeedType(final String title) {
        this.title = title;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }
}

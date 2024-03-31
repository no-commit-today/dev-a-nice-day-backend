package com.nocommittoday.techswipe.domain.rds.provider;

import com.nocommittoday.module.domain.code.DomainCode;

public enum TechBlogType implements DomainCode {

    // 국내 기업
    DOMESTIC_COMPANY("국내 기업"),
    ;

    private final String title;

    TechBlogType(final String title) {
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

package com.nocommittoday.techswipe.domain.rds.content;

import com.nocommittoday.module.domain.code.DomainCode;

public enum TechCategory implements DomainCode {

    BACKEND("백엔드"),
    FRONTEND("프론트엔드"),
    ANDROID("안드로이드"),
    IOS("iOS"),
    INFRA("인프라"),
    MACHINE_LEARNING("인공지능"),
    CLEAN_CODE("클린코드"),
    TEST("테스트"),
    ;

    private final String title;

    TechCategory(final String title) {
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

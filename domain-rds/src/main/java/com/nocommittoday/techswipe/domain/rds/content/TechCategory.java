package com.nocommittoday.techswipe.domain.rds.content;

import com.nocommittoday.module.domain.code.DomainCode;

/**
 * 기타 카테고리는 추후 데이터 확인 후 다른 카테고리로 분리될 수 있음
 */
public enum TechCategory implements DomainCode {

    BACKEND("백엔드"),
    FRONTEND("프론트엔드"),
    ANDROID("안드로이드"),
    IOS("iOS"),
    INFRA("인프라"),
    ETC("기타"),
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

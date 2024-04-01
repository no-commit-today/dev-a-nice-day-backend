package com.nocommittoday.techswipe.domain.rds.prompt;

import com.nocommittoday.module.domain.code.DomainCode;

public enum PromptType implements DomainCode {

    TECH_POST_URL("기술 블로그 URL 제공 - 요약, 카테고리, 키워드"),
    TECH_POST_CONTENT("기술 블로그 본문 제공 - 요약, 카테고리, 키워드"),
    ;

    private final String title;

    PromptType(final String title) {
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

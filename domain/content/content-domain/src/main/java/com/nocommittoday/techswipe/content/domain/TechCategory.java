package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.core.domain.enums.EnumMapperType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TechCategory implements EnumMapperType {

    SERVER("서버 개발"),
    WEB("웹 개발"),
    APP("앱 개발"),
    DATA_ENGINEERING("데이터 엔지니어링"),
    DEVOPS("데브옵스"),
    AI("인공지능"),
    SW_ENGINEERING("SW 엔지니어링"),
    DEV_TOOL("개발 툴"),
    ;

    private final String title;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return this.title;
    }
}

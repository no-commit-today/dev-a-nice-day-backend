package com.nocommittoday.techswipe.domain.content;

import com.nocommittoday.techswipe.domain.core.EnumMapperType;

import java.util.List;

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

    private static final List<TechCategory> valueList = List.of(values());

    public static List<TechCategory> valueList() {
        return valueList;
    }

    private final String title;

    TechCategory(String title) {
        this.title = title;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return this.title;
    }
}

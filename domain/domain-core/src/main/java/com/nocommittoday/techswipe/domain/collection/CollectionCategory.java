package com.nocommittoday.techswipe.domain.collection;

import com.nocommittoday.techswipe.domain.content.TechCategory;
import com.nocommittoday.techswipe.domain.core.EnumMapperType;

import javax.annotation.Nullable;

public enum CollectionCategory implements EnumMapperType {

    SERVER("서버 개발", TechCategory.SERVER),
    WEB("웹 개발", TechCategory.WEB),
    APP("앱 개발", TechCategory.APP),
    DATA_ENGINEERING("데이터 엔지니어링", TechCategory.DATA_ENGINEERING),
    DEVOPS("데브옵스", TechCategory.DEVOPS),
    AI("인공지능", TechCategory.AI),
    SW_ENGINEERING("SW 엔지니어링", TechCategory.SW_ENGINEERING),
    DEV_TOOL("개발 툴", TechCategory.DEV_TOOL),

    NON_DEV("개발 외 주제", null),
    ILLEGAL_CONTENT("잘못된 컨텐츠", null),
    ;

    private final String title;
    private final TechCategory techCategory;

    CollectionCategory(String title, @Nullable TechCategory techCategory) {
        this.title = title;
        this.techCategory = techCategory;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }

    public TechCategory toTechCategory() {
        if (techCategory == null) {
            throw new IllegalStateException("techCategory is null");
        }
        return techCategory;
    }

    public boolean isUsed() {
        return techCategory != null;
    }

}

package com.nocommittoday.techswipe.core.domain;


public record EnumMapperValue(
        String code,
        String title
) {

    public EnumMapperValue(EnumMapperType enumMapperType) {
        this(enumMapperType.getCode(), enumMapperType.getTitle());
    }
}

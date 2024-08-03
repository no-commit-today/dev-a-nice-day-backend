package com.nocommittoday.techswipe.domain.core;


public record EnumMapperValue(
        String code,
        String title
) {

    public EnumMapperValue(EnumMapperType enumMapperType) {
        this(enumMapperType.getCode(), enumMapperType.getTitle());
    }
}

package com.nocommittoday.techswipe.core.domain.enums;


public record EnumMapperValue(
        String code,
        String title
) {

    public EnumMapperValue(final EnumMapperType enumMapperType) {
        this(enumMapperType.getCode(), enumMapperType.getTitle());
    }
}

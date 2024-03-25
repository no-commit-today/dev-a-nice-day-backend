package com.nocommittoday.module.domain.code.api;

import com.nocommittoday.module.domain.code.EnumMapperType;

public record EnumMapperValue(
        String code,
        String title
) {

    public static EnumMapperValue of(final EnumMapperType enumMapperType) {
        return new EnumMapperValue(enumMapperType.getCode(), enumMapperType.getTitle());
    }
}

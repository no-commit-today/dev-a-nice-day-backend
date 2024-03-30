package com.nocommittoday.module.domain.code.api;

import com.nocommittoday.module.domain.code.DomainCode;

public record DomainCodeValue(
        String code,
        String title
) {

    public static DomainCodeValue of(final DomainCode domainCode) {
        return new DomainCodeValue(domainCode.getCode(), domainCode.getTitle());
    }
}

package com.nocommittoday.techswipe.content.domain;

import com.nocommittoday.techswipe.core.domain.exception.ErrorCodeType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechContentErrorCode implements ErrorCodeType {
    PROVIDER_NOT_FOUND("CONTENT-001", "Provider 가 존재하지 않습니다.", 404),
    ;

    private final String code;
    private final String message;
    private final int status;
}

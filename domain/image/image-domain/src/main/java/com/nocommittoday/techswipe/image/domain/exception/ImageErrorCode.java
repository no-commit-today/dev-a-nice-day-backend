package com.nocommittoday.techswipe.image.domain.exception;

import com.nocommittoday.techswipe.core.domain.exception.ErrorCodeType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.net.HttpURLConnection;

@Getter
@RequiredArgsConstructor
public enum ImageErrorCode implements ErrorCodeType {
    NOT_SUPPORTED_IMAGE("IMAGE-001", "지원하지 않는 이미지입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    IMAGE_NOT_FOUND("IMAGE-002", "이미지를 찾을 수 없습니다.", HttpURLConnection.HTTP_NOT_FOUND),
    IMAGE_ILLEGAL_URL("IMAGE-003", "잘못된 이미지 URL입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final int status;
}

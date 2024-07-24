package com.nocommittoday.techswipe.image.domain.exception;

import com.nocommittoday.techswipe.core.domain.ErrorCodeType;
import lombok.Getter;

import java.net.HttpURLConnection;

@Getter
public enum ImageErrorCode implements ErrorCodeType {
    NOT_SUPPORTED_IMAGE("001", "지원하지 않는 이미지입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    IMAGE_NOT_FOUND("002", "이미지를 찾을 수 없습니다.", HttpURLConnection.HTTP_NOT_FOUND),
    IMAGE_ILLEGAL_URL("003", "잘못된 이미지 URL입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final int status;

    ImageErrorCode(final String code, final String message, final int status) {
        this.code = "IMAGE-" + code;
        this.message = message;
        this.status = status;
    }
}

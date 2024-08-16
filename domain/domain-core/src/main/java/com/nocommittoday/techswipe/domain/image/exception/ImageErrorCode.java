package com.nocommittoday.techswipe.domain.image.exception;

import com.nocommittoday.techswipe.domain.core.ErrorCodeType;

import java.net.HttpURLConnection;

public enum ImageErrorCode implements ErrorCodeType {
    NOT_SUPPORTED_IMAGE("지원하지 않는 이미지입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    IMAGE_NOT_FOUND("이미지를 찾을 수 없습니다.", HttpURLConnection.HTTP_NOT_FOUND),
    IMAGE_ILLEGAL_URL("잘못된 이미지 URL입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ;

    private final String message;
    private final int status;

    ImageErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }


    @Override
    public String getCode() {
        return "IMAGE-" + name();
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public int getStatus() {
        return this.status;
    }
}

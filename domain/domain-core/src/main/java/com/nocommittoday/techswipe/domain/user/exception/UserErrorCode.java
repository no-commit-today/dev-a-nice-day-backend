package com.nocommittoday.techswipe.domain.user.exception;

import com.nocommittoday.techswipe.domain.core.ErrorCodeType;

import java.net.HttpURLConnection;

public enum UserErrorCode  implements ErrorCodeType {

    AUTHENTICATION_FAILURE("001", "인증에 실패하였습니다.", HttpURLConnection.HTTP_UNAUTHORIZED),
    SIGNUP_UNSUPPORTED_PROVIDER("002", "지원하지 않는 OAuth2 제공자입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final int status;

    UserErrorCode(String code, String message, int status) {
        this.code = "USER-" + code;
        this.message = message;
        this.status = status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getStatus() {
        return status;
    }
}

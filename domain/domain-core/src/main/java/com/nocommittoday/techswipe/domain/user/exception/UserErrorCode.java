package com.nocommittoday.techswipe.domain.user.exception;

import com.nocommittoday.techswipe.domain.core.ErrorCodeType;

import java.net.HttpURLConnection;

public enum UserErrorCode  implements ErrorCodeType {

    AUTHENTICATION_FAILURE("인증에 실패하였습니다.", HttpURLConnection.HTTP_UNAUTHORIZED),
    SIGNUP_UNSUPPORTED_PROVIDER("지원하지 않는 OAuth2 제공자입니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ALREADY_EXISTS("이미 존재하는 사용자입니다.", HttpURLConnection.HTTP_CONFLICT),
    NOT_FOUND("사용자를 찾을 수 없습니다.", HttpURLConnection.HTTP_NOT_FOUND),
    AUTHENTICATION_REQUIRED("인증이 필요합니다.", HttpURLConnection.HTTP_UNAUTHORIZED),
    NOT_ALLOWED("허용되지 않은 요청입니다.", HttpURLConnection.HTTP_FORBIDDEN),
    ;

    private final String message;
    private final int status;

    UserErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getCode() {
        return "USER-" + name();
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

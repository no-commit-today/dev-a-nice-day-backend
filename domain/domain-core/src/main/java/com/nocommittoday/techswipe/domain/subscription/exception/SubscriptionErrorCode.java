package com.nocommittoday.techswipe.domain.subscription.exception;

import com.nocommittoday.techswipe.domain.core.ErrorCodeType;

import java.net.HttpURLConnection;

public enum SubscriptionErrorCode implements ErrorCodeType {

    NOT_FOUND("구독 정보가 존재하지 않습니다.", HttpURLConnection.HTTP_NOT_FOUND),
    ;

    private final String message;
    private final int status;

    SubscriptionErrorCode(String message, int status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String getCode() {
        return "SUBSCRIPTION-" + name();
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

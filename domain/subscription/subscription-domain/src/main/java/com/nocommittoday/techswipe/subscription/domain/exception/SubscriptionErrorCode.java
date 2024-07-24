package com.nocommittoday.techswipe.subscription.domain.exception;

import com.nocommittoday.techswipe.core.domain.ErrorCodeType;
import lombok.Getter;

import java.net.HttpURLConnection;

@Getter
public enum SubscriptionErrorCode implements ErrorCodeType {


    NOT_FOUND("001", "구독 정보가 존재하지 않습니다.", HttpURLConnection.HTTP_NOT_FOUND),
    REGISTER_FAILURE("002", "구독 정보 등록에 실패하였습니다.", HttpURLConnection.HTTP_BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final int status;

    SubscriptionErrorCode(final String code, final String message, final int status) {
        this.code = "SUBSCRIPTION-" + code;
        this.message = message;
        this.status = status;
    }
}

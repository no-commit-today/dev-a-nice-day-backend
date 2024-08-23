package com.nocommittoday.techswipe.infrastructure.aws.image;

import com.nocommittoday.techswipe.client.core.ClientException;

public class NotImageTypeException extends ClientException {

    public NotImageTypeException(String url) {
        super("이미지 타입이 아닙니다. url: " + url);
    }
}

package com.nocommittoday.techswipe.image.infrastructure;

import com.nocommittoday.techswipe.client.core.ClientException;

public class NotImageTypeException extends ClientException {

    public NotImageTypeException(final String url) {
        super("이미지 타입이 아닙니다. url: " + url);
    }
}

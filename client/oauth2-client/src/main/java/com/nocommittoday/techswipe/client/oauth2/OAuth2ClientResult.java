package com.nocommittoday.techswipe.client.oauth2;

import feign.FeignException;

public record OAuth2ClientResult<T>(
        Type type,
        T response,
        RuntimeException exception
) {

    public static <T> OAuth2ClientResult<T> ok(T response) {
        return new OAuth2ClientResult<>(Type.OK, response, null);
    }

    public static <T> OAuth2ClientResult<T> unauthorized(FeignException.Unauthorized ex) {
        return new OAuth2ClientResult<>(Type.UNAUTHORIZED, null, ex);
    }

    public T getResponse() {
        if (exception != null) {
            throw exception;
        }
        return response;
    }

    public enum Type {
        OK,
        UNAUTHORIZED,
        ;
    }
}

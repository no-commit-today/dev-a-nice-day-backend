package com.nocommittoday.techswipe.infrastructure.web;

import javax.annotation.Nullable;
import java.util.Objects;

public class ClientResponse<T> {

    private final ClientResponseType type;
    @Nullable private final T data;
    @Nullable private final ClientException exception;

    private ClientResponse(ClientResponseType type, @Nullable T data, @Nullable ClientException exception) {
        this.type = type;
        this.data = data;
        this.exception = exception;
    }

    public static <T> ClientResponse<T> success(T data) {
        return new ClientResponse<>(ClientResponseType.SUCCESS, data, null);
    }

    public static <T> ClientResponse<T> fail(ClientResponseType type, Exception ex) {
        return new ClientResponse<>(type, null, new ClientException(ex));
    }

    public ClientResponseType getType() {
        return type;
    }

    public T getData() {
        if (ClientResponseType.SUCCESS != type) {
            throw new IllegalStateException("클라이언트 응답이 실패해서 데이터를 가져올 수 없습니다.");
        }
        return Objects.requireNonNull(data);
    }

    public ClientException getException() {
        if (ClientResponseType.SUCCESS == type) {
            throw new IllegalStateException("클라이언트 응답이 성공해서 예외를 가져올 수 없습니다.");
        }
        return Objects.requireNonNull(exception);
    }
}

package com.nocommittoday.techswipe.infrastructure.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.Nullable;
import java.util.Objects;

public class ClientResponse<T> {

    @Nullable private final T data;
    @Nullable private final ClientException exception;

    private ClientResponse(@Nullable T data, @Nullable ClientException exception) {
        this.data = data;
        this.exception = exception;
    }

    public static <T> ClientResponse<T> success(T data) {
        return new ClientResponse<>(data, null);
    }

    public static <T> ClientResponse<T> fail(HttpClientErrorException ex) {
        return switch (ex.getStatusCode()) {
            case HttpStatus.NOT_FOUND -> new ClientResponse<>(
                    null, new ClientException.NotFound(ex));
            case HttpStatus.UNAUTHORIZED -> new ClientResponse<>(
                    null, new ClientException.Unauthorized(ex));
            default -> new ClientResponse<>(null, new ClientException(ex));
        };
    }

    public boolean isSuccess() {
        return this.exception == null;
    }

    public boolean isError() {
        return this.exception != null;
    }

    public boolean isNotFound() {
        return this.exception instanceof ClientException.NotFound;
    }

    public boolean isUnauthorized() {
        return this.exception instanceof ClientException.Unauthorized;
    }

    public T getData() {
        if (!isSuccess()) {
            throw Objects.requireNonNull(this.exception);
        }
        return Objects.requireNonNull(data);
    }

    public ClientException getException() {
        if (!isError()) {
            throw new IllegalStateException("클라이언트 응답이 성공해서 예외를 가져올 수 없습니다.");
        }
        return Objects.requireNonNull(exception);
    }
}

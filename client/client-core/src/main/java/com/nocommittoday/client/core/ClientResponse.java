package com.nocommittoday.client.core;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class ClientResponse<T> {

    private final ClientResponseType type;

    private final T data;

    private final ClientException exception;

    private ClientResponse(
            final ClientResponseType type,
            final T data,
            final ClientException exception
    ) {
        this.type = type;
        this.data = data;
        this.exception = exception;
    }

    public static ClientResponse<Void> success() {
        return new ClientResponse<>(ClientResponseType.SUCCESS, null, null);
    }

    public static <T> ClientResponse<T> success(final T data) {
        return new ClientResponse<>(ClientResponseType.SUCCESS, data, null);
    }

    public static <T> ClientResponse<T> notFound(final Exception exception) {
        return new ClientResponse<>(ClientResponseType.NOT_FOUND, null, new ClientException(exception));
    }

    public static <T> ClientResponse<T> failed(final ClientException ex) {
        return new ClientResponse<>(ClientResponseType.FAILED, null, ex);
    }

    public static <T> ClientResponse<T> failed(final HttpClientErrorException ex) {
        if (HttpStatus.NOT_FOUND ==  ex.getStatusCode()) {
            return new ClientResponse<>(ClientResponseType.NOT_FOUND, null, new ClientException(ex));
        }
        return new ClientResponse<>(ClientResponseType.FAILED, null, new ClientException(ex));
    }

    public static <T> ClientResponse<T> failed(final Exception ex) {
        if (ex instanceof HttpClientErrorException httpClientErrorException) {
            return failed(httpClientErrorException);
        } else if (ex instanceof ClientException clientException) {
            return failed(clientException);
        }
        return new ClientResponse<>(ClientResponseType.FAILED, null, new ClientException(ex));
    }

    public ClientResponseType getType() {
        return type;
    }

    public T getData() {
        return data;
    }

    public ClientException getException() {
        return exception;
    }

    public boolean isSuccess() {
        return ClientResponseType.SUCCESS == type;
    }

    public boolean isNotFound() {
        return ClientResponseType.NOT_FOUND == type;
    }

    public boolean isFailed() {
        return ClientResponseType.FAILED == type;
    }

    public T get() {
        if (!isSuccess()) {
            throw exception;
        }
        return data;
    }
}

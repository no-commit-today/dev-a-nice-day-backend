package com.nocommittoday.module.exception.handler.servlet;

public record FieldErrorResponse(
        String field,
        Object value,
        String reason
) {
}

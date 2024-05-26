package com.nocommittoday.techswipe.core.adapter.in.web.servlet;

public record FieldErrorResponse(
        String field,
        Object value,
        String reason
) {
}

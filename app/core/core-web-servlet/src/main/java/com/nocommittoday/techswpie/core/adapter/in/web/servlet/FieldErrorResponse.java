package com.nocommittoday.techswpie.core.adapter.in.web.servlet;

public record FieldErrorResponse(
        String field,
        Object value,
        String reason
) {
}

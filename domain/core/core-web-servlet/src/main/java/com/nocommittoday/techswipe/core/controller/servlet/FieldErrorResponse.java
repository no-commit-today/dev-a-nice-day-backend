package com.nocommittoday.techswipe.core.controller.servlet;

public record FieldErrorResponse(
        String field,
        Object value,
        String reason
) {
}

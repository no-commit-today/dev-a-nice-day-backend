package com.nocommittoday.techswipe.controller.core;

public record FieldErrorResponse(
        String field,
        Object value,
        String reason
) {
}

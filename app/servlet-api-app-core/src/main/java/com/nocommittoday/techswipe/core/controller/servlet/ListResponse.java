package com.nocommittoday.techswipe.core.controller.servlet;

import java.util.List;

public record ListResponse<T>(
        List<T> content
) {
}

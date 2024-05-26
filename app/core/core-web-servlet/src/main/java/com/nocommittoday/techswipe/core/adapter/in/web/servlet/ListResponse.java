package com.nocommittoday.techswipe.core.adapter.in.web.servlet;

import java.util.List;

public record ListResponse<T>(
        List<T> content
) {
}

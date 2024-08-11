package com.nocommittoday.techswipe.controller.core;

import java.util.List;

public record ListResponse<T>(
        List<T> content
) {
}
